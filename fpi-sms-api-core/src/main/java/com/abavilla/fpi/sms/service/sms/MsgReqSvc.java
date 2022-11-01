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

package com.abavilla.fpi.sms.service.sms;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.abavilla.fpi.fw.exceptions.ApiSvcEx;
import com.abavilla.fpi.fw.service.AbsSvc;
import com.abavilla.fpi.sms.dto.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.sms.dto.api.m360.M360ResponseDto;
import com.abavilla.fpi.sms.entity.sms.MsgReq;
import com.abavilla.fpi.sms.entity.sms.StateEncap;
import com.abavilla.fpi.sms.ext.dto.MsgReqDto;
import com.abavilla.fpi.sms.ext.dto.MsgReqStatusDto;
import com.abavilla.fpi.sms.mapper.sms.MsgReqMapper;
import com.abavilla.fpi.telco.ext.entity.enums.ApiStatus;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class MsgReqSvc extends AbsSvc<MsgReqDto, MsgReq> {

  @Inject
  MsgReqMapper msgReqMapper;

  @Inject
  M360Svc m360Svc;

  public Uni<MsgReqStatusDto> sendMsg(MsgReqDto msgReqDto) {
    Uni<BroadcastResponseDto> broadcastResponseDtoUni = m360Svc.sendMsg(msgReqDto);
    Log.debug("message request: " + msgReqDto);
    return broadcastResponseDtoUni
        .onFailure().recoverWithItem(ex-> {  // | api failure
          Log.error("m360 api error: " + ex);
          BroadcastResponseDto broadcastResponseDto = new BroadcastResponseDto();
          if (ex instanceof ApiSvcEx apiEx) {
            broadcastResponseDto = m360Svc.mapResponseToDto(apiEx.getJsonResponse(M360ResponseDto.class));
          }
          return broadcastResponseDto;
        })
        .chain(respDto -> {
          MsgReq msgReq = msgReqMapper.mapFromResponse(respDto);
          msgReq.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
          msgReq.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
          var stateItem = new StateEncap(ApiStatus.WAIT, LocalDateTime.now(ZoneOffset.UTC));
          msgReq.setApiStatus(List.of(stateItem));
          return repo.persist(msgReq).onFailure().recoverWithNull(); // if failed to save to mongo, return null
        })
        .map(respMongo -> {
          Log.debug("mongo save: " + respMongo);  // receive request from to save to mongo
          MsgReqStatusDto status = new MsgReqStatusDto();
          if (respMongo != null) {
            status.setStatus(respMongo
                .getName().equalsIgnoreCase("Created") ? 0 : 1);  // if both mongo and api is success
          } else {
            status.setStatus(2); // mongo failure
          }
          return status;
        });
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
