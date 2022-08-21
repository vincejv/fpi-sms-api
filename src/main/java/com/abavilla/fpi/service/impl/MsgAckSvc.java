package com.abavilla.fpi.service.impl;

import com.abavilla.fpi.dto.impl.NullDto;
import com.abavilla.fpi.entity.enums.ApiStatus;
import com.abavilla.fpi.entity.impl.LeakAck;
import com.abavilla.fpi.entity.impl.MsgReq;
import com.abavilla.fpi.entity.impl.StateEncap;
import com.abavilla.fpi.exceptions.ApiSvcEx;
import com.abavilla.fpi.repo.impl.MsgReqRepo;
import com.abavilla.fpi.service.AbsSvc;
import com.abavilla.fpi.util.MapperUtil;
import io.smallrye.mutiny.Uni;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.microprofile.context.ManagedExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MsgAckSvc extends AbsSvc<NullDto, LeakAck>  {

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
    LocalDateTime ackTime = MapperUtil.convertLdtUTC8ToUtc(LocalDateTime.parse(ackTimestamp,
        DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
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
