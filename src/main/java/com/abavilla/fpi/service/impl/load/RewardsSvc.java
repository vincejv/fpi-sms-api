package com.abavilla.fpi.service.impl.load;

import com.abavilla.fpi.dto.impl.api.gl.load.RewardsReqDto;
import com.abavilla.fpi.dto.impl.api.gl.load.RewardsRespDto;
import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.exceptions.ApiSvcEx;
import com.abavilla.fpi.mapper.gl.load.LoadRespMapper;
import com.abavilla.fpi.mapper.gl.load.RewardsReqMapper;
import com.abavilla.fpi.repo.impl.gl.load.GLLoadApiRepo;
import com.abavilla.fpi.repo.impl.load.RewardsTransRepo;
import com.abavilla.fpi.service.AbsSvc;
import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ApplicationScoped
public class RewardsSvc extends AbsSvc<RewardsReqDto, RewardsTransStatus> {
  @RestClient
  GLLoadApiRepo loadApi;

  @Inject
  RewardsTransRepo advRepo;

  @Inject
  RewardsReqMapper rewardsMapper;
  @Inject
  LoadRespMapper loadRespMapper;

  @ConfigProperty(name = "ph.com.gl.app-id")
  String appId;

  @ConfigProperty(name = "ph.com.gl.app-secret")
  String appSecret;

  @ConfigProperty(name = "ph.com.gl.rewards.token")
  String amaxToken;

  public Uni<Response> reloadNumber(LoadReqDto loadReq) {
    var apiReqBody = new RewardsReqDto.Body();
    apiReqBody.setAppId(appId);
    apiReqBody.setAppSecret(appSecret);
    apiReqBody.setRewardsToken(amaxToken);

    apiReqBody.setAddress(loadReq.getMobile());
    apiReqBody.setSku(loadReq.getSku());

    var apiReq = new RewardsReqDto();
    apiReq.setBody(apiReqBody);

    var log = new RewardsTransStatus();
    rewardsMapper.mapRequestDtoToEntity(apiReq, log);

    return
        loadApi.sendLoad(apiReq)
            .onFailure(ApiSvcEx.class).recoverWithItem(ex -> {
              var apiEx = (ApiSvcEx) ex;
              var resp = apiEx.getJsonResponse(RewardsRespDto.class);
              resp.chainEx(apiEx);
              return resp;
            })
            .chain(resp -> {
              rewardsMapper.mapRespDtoToEntity(resp, log);
              log.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
              log.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
              return repo.persist(log).chain(()->Uni.createFrom().item(log));
            })
            .map(rewardsTransStatus -> {
              var dtoResp = loadRespMapper.mapToDto(rewardsTransStatus.getResponse());
              if (rewardsTransStatus.lastEx() == null) {
                return Response.ok().entity(dtoResp).build();
              } else {
                if (rewardsTransStatus.lastEx() instanceof ApiSvcEx) {
                  return Response.status(((ApiSvcEx)rewardsTransStatus.lastEx())
                      .getHttpResponseStatus().code()).entity(dtoResp).build();
                } else {
                  return Response.serverError().entity(dtoResp).build();
                }
              }
            });
  }

  @Override
  public RewardsReqDto mapToDto(RewardsTransStatus entity) {
    throw new NotImplementedException();
  }

  @Override
  public RewardsTransStatus mapToEntity(RewardsReqDto dto) {
    throw new NotImplementedException();
  }
}
