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

package com.abavilla.fpi.service.impl.load.gl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.abavilla.fpi.dto.impl.api.load.dtone.DVSCallbackDto;
import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsCallbackDto;
import com.abavilla.fpi.entity.enums.ApiStatus;
import com.abavilla.fpi.entity.impl.load.CallBack;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.entity.mongo.AbsMongoItem;
import com.abavilla.fpi.exceptions.ApiSvcEx;
import com.abavilla.fpi.mapper.load.dtone.DTOneMapper;
import com.abavilla.fpi.mapper.load.gl.GLMapper;
import com.abavilla.fpi.repo.impl.load.RewardsLeakRepo;
import com.abavilla.fpi.repo.impl.load.RewardsTransRepo;
import com.abavilla.fpi.service.AbsSvc;
import com.abavilla.fpi.util.AbavillaConst;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.context.ManagedExecutor;

@ApplicationScoped
public class RewardsCallbackSvc extends AbsSvc<GLRewardsCallbackDto, RewardsTransStatus> {

  @Inject
  RewardsTransRepo advRepo;

  @Inject
  RewardsLeakRepo leakRepo;

  @Inject
  DTOneMapper dtOneMapper;

  @Inject
  GLMapper glMapper;

  /**
   * Runs background tasks from webhook
   */
  @Inject
  ManagedExecutor executor;

  public Uni<Void> storeCallback(GLRewardsCallbackDto dto) {
    ApiStatus status = ApiStatus.ACK;
    return storeCallback(glMapper.mapGLCallbackDtoToEntity(dto),
        status, AbavillaConst.PROV_GL, dto.getBody().getTransactionId());
  }

  public Uni<Void> storeCallback(DVSCallbackDto dto) {
    ApiStatus status = ApiStatus.ACK;
    return storeCallback(dtOneMapper.mapDTOneRespToEntity(dto),
        status, AbavillaConst.PROV_DTONE, dto.getDtOneId());
  }

  private Uni<Void> storeCallback(AbsMongoItem field, ApiStatus status,
                                  String provider, Long transactionId) {
    var byTransId = advRepo.findByRespTransIdAndProvider(
        String.valueOf(transactionId), provider);
    executor.execute(() -> {
      byTransId.chain(rewardsTransStatusOpt -> {
            if (rewardsTransStatusOpt.isPresent()) {
              return Uni.createFrom().item(rewardsTransStatusOpt.get());
            } else {
              throw new ApiSvcEx("Trans Id for rewards callback not found: " + transactionId);
            }
          })
          .onFailure().retry().withBackOff(Duration.ofSeconds(3)).withJitter(0.2)
          .atMost(5) // Retry for item not found and nothing else
          .chain(rewardsTrans -> {
            //rewardsMapper.mapCallbackDtoToEntity(dto, rewardsTrans);
            CallBack callBack = new CallBack();
            callBack.setContent(field);
            callBack.setDateReceived(LocalDateTime.now(ZoneOffset.UTC));
            callBack.setStatus(status);
            rewardsTrans.getApiCallback().add(callBack);
            rewardsTrans.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
            return repo.persistOrUpdate(rewardsTrans);
          }).onFailure().call(ex -> { // leaks/delay
            field.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
            field.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
            return leakRepo.persist(field);
          }).onFailure().recoverWithNull().await().indefinitely();
    });

    return Uni.createFrom().voidItem();
  }

  @Override
  public GLRewardsCallbackDto mapToDto(RewardsTransStatus entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public RewardsTransStatus mapToEntity(GLRewardsCallbackDto dto) {
    //return rewardsMapper.mapCallbackDtoToEntity(dto);
    throw new UnsupportedOperationException();
  }
}
