package com.abavilla.fpi.service.impl.gl.load;

import com.abavilla.fpi.dto.impl.api.gl.load.RewardsCallbackDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.exceptions.ApiSvcEx;
import com.abavilla.fpi.mapper.gl.load.RewardsReqMapper;
import com.abavilla.fpi.repo.impl.load.RewardsLeakRepo;
import com.abavilla.fpi.repo.impl.load.RewardsTransRepo;
import com.abavilla.fpi.service.AbsSvc;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.context.ManagedExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ApplicationScoped
public class RewardsCallbackSvc extends AbsSvc<RewardsCallbackDto, RewardsTransStatus> {
  @Inject
  RewardsReqMapper rewardsMapper;
  @Inject
  RewardsTransRepo advRepo;
  @Inject
  RewardsLeakRepo leakRepo;

  /**
   * Runs background tasks from webhook
   */
  @Inject
  ManagedExecutor executor;

  public Uni<Void> storeCallback(RewardsCallbackDto dto) {
    var byTransId = advRepo.findByRespTransId(dto.getBody().getTransactionId());
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
            rewardsMapper.mapCallbackDtoToEntity(dto, rewardsTrans);
            rewardsTrans.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
            return repo.persistOrUpdate(rewardsTrans);
          }).onFailure().call(ex -> { // leaks/delay
            var leakEntity = rewardsMapper.mapCallbackDtoToCallbackEntity(dto);
            leakEntity.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
            leakEntity.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
            return leakRepo.persist(leakEntity);
          }).onFailure().recoverWithNull().await().indefinitely();
    });

    return Uni.createFrom().voidItem();
  }

  @Override
  public RewardsCallbackDto mapToDto(RewardsTransStatus entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public RewardsTransStatus mapToEntity(RewardsCallbackDto dto) {
    //return rewardsMapper.mapCallbackDtoToEntity(dto);
    throw new UnsupportedOperationException();
  }
}
