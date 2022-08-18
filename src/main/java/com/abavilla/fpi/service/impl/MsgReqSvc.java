package com.abavilla.fpi.service.impl;

import com.abavilla.fpi.dto.impl.MsgReqDto;
import com.abavilla.fpi.dto.impl.MsgReqStatusDto;
import com.abavilla.fpi.dto.impl.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.entity.enums.ApiStatus;
import com.abavilla.fpi.entity.impl.MsgReq;
import com.abavilla.fpi.entity.impl.StateEncap;
import com.abavilla.fpi.exceptions.ApiSvcEx;
import com.abavilla.fpi.mapper.MsgReqMapper;
import com.abavilla.fpi.service.AbsSvc;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class MsgReqSvc extends AbsSvc<MsgReqDto, MsgReq> {
  @Inject
  MsgReqMapper msgReqMapper;
  @Inject
  M360Svc m360Svc;

  public Uni<MsgReq> save(MsgReq entity) {
    return repo.persist(entity);
  }

  public Uni<MsgReqStatusDto> sendMsg(MsgReqDto msgReqDto) {
    Uni<BroadcastResponseDto> broadcastResponseDtoUni = m360Svc.sendMsg(msgReqDto);
    Log.info("message request: " + msgReqDto);
    return broadcastResponseDtoUni
        .onFailure().recoverWithItem(ex-> {  // | api failure
          Log.error("m360 api error: " + ex);
          BroadcastResponseDto broadcastResponseDto = new BroadcastResponseDto();
          if (ex instanceof ApiSvcEx)
            broadcastResponseDto = m360Svc.mapResponseToDto(((ApiSvcEx) ex).getJsonResponse());
          return broadcastResponseDto;
        })
        .chain(respDto -> {
          MsgReq msgReq = msgReqMapper.mapFromResponse(respDto);
          msgReq.setDateCreated(LocalDateTime.now());
          msgReq.setDateUpdated(LocalDateTime.now());
          var stateItem = new StateEncap(ApiStatus.WAIT, LocalDateTime.now());
          msgReq.setApiStatus(List.of(stateItem));
          return repo.persist(msgReq).onFailure().recoverWithNull(); // if failed to save to mongo, return null
        })
        .map(respMongo -> {
          Log.info("mongo save: " + respMongo);  // receive request from to save to mongo
          MsgReqStatusDto status = new MsgReqStatusDto();
          if (respMongo != null) {
            status.setStatus(respMongo
                .getName().equalsIgnoreCase("Created") ? 0 : 1);  // if both mongo and api is success
          } else {
            status.setStatus(2); // mongo failure
          }
          return status;
        }); // if you chain failure it will replace it
//        .onFailure().recoverWithItem(mongoEx -> {
//          Log.info("mongo failure");
//          MsgReqStatusDto status = new MsgReqStatusDto(); // will go here if mongo failure
//          status.setStatus(2); // mongo failure
//          return status;
//        });
  }

  @Override
  public MsgReqDto mapToDto(MsgReq entity) {
    return msgReqMapper.mapToDto(entity);
  }

  @Override
  public MsgReq mapToEntity(MsgReqDto dto) {
    return msgReqMapper.mapToEntity(dto);
  }
}
