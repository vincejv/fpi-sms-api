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

import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsCallbackDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.exceptions.ApiSvcEx;
import com.abavilla.fpi.mapper.load.RewardsTransStatusMapper;
import com.abavilla.fpi.repo.impl.load.RewardsLeakRepo;
import com.abavilla.fpi.repo.impl.load.RewardsTransRepo;
import com.abavilla.fpi.service.AbsSvc;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.context.ManagedExecutor;

@ApplicationScoped
public class RewardsCallbackSvc extends AbsSvc<GLRewardsCallbackDto, RewardsTransStatus> {
  @Inject
  RewardsTransStatusMapper rewardsMapper;
  @Inject
  RewardsTransRepo advRepo;
  @Inject
  RewardsLeakRepo leakRepo;

  /**
   * Runs background tasks from webhook
   */
  @Inject
  ManagedExecutor executor;

  public Uni<Void> storeCallback(GLRewardsCallbackDto dto) {
    var byTransId = advRepo.findByRespTransId(
        String.valueOf(dto.getBody().getTransactionId()));
    executor.execute(() -> {
      byTransId.chain(rewardsTransStatusOpt -> {
            if (rewardsTransStatusOpt.isPresent()) {
              return Uni.createFrom().item(rewardsTransStatusOpt.get());
            } else {
              throw new ApiSvcEx("Trans Id for rewards callback not found: " + dto.getBody().getTransactionId());
            }
          })
          .onFailure().retry().withBackOff(Duration.ofSeconds(3)).withJitter(0.2)
          .atMost(5) // Retry for item not found and nothing else
          .chain(rewardsTrans -> {
            //rewardsMapper.mapCallbackDtoToEntity(dto, rewardsTrans);
            rewardsTrans.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
            return repo.persistOrUpdate(rewardsTrans);
          }).onFailure().call(ex -> { // leaks/delay
            //var leakEntity = rewardsMapper.mapCallbackDtoToCallbackEntity(dto);
            //leakEntity.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
            //leakEntity.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
            //return leakRepo.persist(leakEntity);
        return null;
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
