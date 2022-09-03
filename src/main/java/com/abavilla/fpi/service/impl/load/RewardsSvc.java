package com.abavilla.fpi.service.impl.load;

import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsReqDto;
import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.engine.load.LoadEngine;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.mapper.load.LoadReqEntityMapper;
import com.abavilla.fpi.mapper.load.RewardsTransStatusMapper;
import com.abavilla.fpi.service.AbsSvc;
import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.NotImplementedException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ApplicationScoped
public class RewardsSvc extends AbsSvc<GLRewardsReqDto, RewardsTransStatus> {

  @Inject
  LoadReqEntityMapper loadReqMapper;
  @Inject
  RewardsTransStatusMapper dtoToEntityMapper;

  @Inject
  PromoSkuSvc promoSkuSvc;
  @Inject
  LoadEngine loadEngine;

  public Uni<Response> reloadNumber(LoadReqDto loadReqDto) {
    return promoSkuSvc.findSku(loadReqDto).chain(promo -> {
      ILoadProviderSvc loadSvc = null;
      if (promo.isPresent()) {
        loadSvc = loadEngine.getProvider(promo.get());
      }
      // create log to db
      var log = new RewardsTransStatus();
      var loadReq = loadReqMapper.mapToEntity(loadReqDto);
      log.setLoadRequest(loadReq);

      final var loadSvcProvider = loadSvc;
      if (loadSvcProvider != null) {
        log.setLoadProvider(loadSvc.getProviderName());
        log.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
        log.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
        var logJob = repo.persist(log);
        return logJob
            .chain(logEntity -> {
              loadReqDto.setTransactionId(logEntity.getId().toString());
              return loadSvcProvider.reload(loadReqDto, promo.get())
                  .chain(loadRespDto -> {
                    // logEntity.setRawApiResponse(loadRespDto.getRawApiResponse()); // replace with mapper code
                    dtoToEntityMapper.mapLoadRespDtoToEntity(
                        loadRespDto, logEntity
                    );
                    log.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
                    return repo.persistOrUpdate(logEntity)
                        .map(res -> loadRespDto);
                  });
            })
            .map(loadRespDto -> Response.ok()
                .entity(loadRespDto)
                .build());
      } else {
        return Uni.createFrom().item(Response
            .status(Response.Status.NOT_FOUND)
            .entity("No Load provider available")
            .build());
      }
    });

//    return
//        loadApi.sendLoad(apiReq)
//            .onFailure(ApiSvcEx.class).recoverWithItem(ex -> {
//              var apiEx = (ApiSvcEx) ex;
//              var resp = apiEx.getJsonResponse(RewardsRespDto.class);
//              resp.chainEx(apiEx);
//              return resp;
//            })
//            .chain(resp -> {
//              rewardsMapper.mapRespDtoToEntity(resp, log);
//              log.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
//              log.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
//              return repo.persist(log).chain(()->Uni.createFrom().item(log));
//            })
//            .map(rewardsTransStatus -> {
//              var dtoResp = loadRespMapper.mapToDto(rewardsTransStatus.getResponse());
//              if (rewardsTransStatus.lastEx() == null) {
//                return Response.ok().entity(dtoResp).build();
//              } else {
//                if (rewardsTransStatus.lastEx() instanceof ApiSvcEx) {
//                  return Response.status(((ApiSvcEx)rewardsTransStatus.lastEx())
//                      .getHttpResponseStatus().code()).entity(dtoResp).build();
//                } else {
//                  return Response.serverError().entity(dtoResp).build();
//                }
//              }
//            });
  }

  @Override
  public GLRewardsReqDto mapToDto(RewardsTransStatus entity) {
    throw new NotImplementedException();
  }

  @Override
  public RewardsTransStatus mapToEntity(GLRewardsReqDto dto) {
    throw new NotImplementedException();
  }
}
