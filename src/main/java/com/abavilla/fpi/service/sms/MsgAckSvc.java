/******************************************************************************
 * FPI Application - Abavilla                                                 *
 * Copyright (C) 2022  Vince Jerald Villamora                                 *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.     *
 ******************************************************************************/

package com.abavilla.fpi.service.sms;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.abavilla.fpi.entity.enums.ApiStatus;
import com.abavilla.fpi.entity.sms.LeakAck;
import com.abavilla.fpi.entity.sms.MsgReq;
import com.abavilla.fpi.entity.sms.StateEncap;
import com.abavilla.fpi.fw.dto.impl.NullDto;
import com.abavilla.fpi.fw.exceptions.ApiSvcEx;
import com.abavilla.fpi.fw.service.AbsSvc;
import com.abavilla.fpi.fw.util.DateUtil;
import com.abavilla.fpi.repo.sms.MsgReqRepo;
import io.smallrye.mutiny.Uni;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.microprofile.context.ManagedExecutor;

@ApplicationScoped
public class MsgAckSvc extends AbsSvc<NullDto, LeakAck> {

  public static final int MAX_RETRIES = 5;
  @Inject
  MsgReqRepo msgReqRepo;

  /**
   * Runs background tasks from webhook
   */
  @Inject
  ManagedExecutor executor;

  public Uni<Void> acknowledge(String msgId, String ackStsCde, String ackTimestamp) {
    ApiStatus apiStatus = ApiStatus.fromId(Integer.parseInt(ackStsCde));
    LocalDateTime ackTime = DateUtil.convertLdtUTC8ToUtc(LocalDateTime.parse(ackTimestamp,
        DateTimeFormatter.ofPattern(DateUtil.M360_TIMESTAMP_FORMAT)));
    Uni<Optional<MsgReq>> byMsgId = msgReqRepo.findByMsgId(msgId);

    /* run in background, immediately return response to webhook */
    executor.execute(() -> {
      byMsgId.chain(msgReqOpt -> {
            if (msgReqOpt.isPresent()) {
              return Uni.createFrom().item(msgReqOpt.get());
            } else {
              throw new ApiSvcEx("Message Id for acknowledgement not found: " + msgId);
            }
          })
          .onFailure().retry().withBackOff(Duration.ofSeconds(3)).withJitter(0.2)
          .atMost(5) // Retry for item not found and nothing else
          .chain(msgReq -> {
            var stateItem = new StateEncap(apiStatus, ackTime);
            if (CollectionUtils.isEmpty(msgReq.getApiStatus())) {
              msgReq.setApiStatus(List.of(stateItem));
            } else {
              msgReq.getApiStatus().add(stateItem);
            }
            msgReq.setLastAcknowledgement(ackTime);
            msgReq.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
            return msgReqRepo.persistOrUpdate(msgReq);
          })
          .onFailure().call(ex -> {
            LeakAck leak = new LeakAck();
            leak.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
            leak.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
            leak.setMsgId(msgId);
            leak.setApiStatus(apiStatus);
            leak.setTimestamp(ackTime);
            return repo.persist(leak);
          }).onFailure().recoverWithNull() // still has to recover from Exception (LateAck)
          .await().indefinitely();
    });

    return Uni.createFrom().voidItem();
  }

  @Override
  public NullDto mapToDto(LeakAck entity) {
    return null;
  }

  @Override
  public LeakAck mapToEntity(NullDto dto) {
    return null;
  }
}
