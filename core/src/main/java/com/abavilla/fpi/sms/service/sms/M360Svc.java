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

import java.util.stream.Collectors;

import com.abavilla.fpi.fw.exceptions.ApiSvcEx;
import com.abavilla.fpi.fw.service.ISvc;
import com.abavilla.fpi.sms.dto.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.sms.ext.dto.MsgReqDto;
import com.abavilla.fpi.sms.mapper.m360.BroadcastResponseMapper;
import com.vincejv.m360.M360ApiClient;
import com.vincejv.m360.dto.ApiError;
import com.vincejv.m360.dto.SMSRequest;
import com.vincejv.m360.exception.M360ApiException;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class M360Svc implements ISvc {

  @Inject
  M360ApiClient m360client;

  @Inject
  BroadcastResponseMapper mapper;

  public Uni<BroadcastResponseDto> sendMsg(MsgReqDto msgReqDto) {
    Log.debug("m360 message request: " + msgReqDto);
    var smsRequest = new SMSRequest(msgReqDto.getMobileNumber(),
      msgReqDto.getContent());

    var m360Resp = Uni.createFrom().future(() ->
      m360client.sendBroadcastMessage(smsRequest));
    return m360Resp.map(resp -> {
      var dto = new BroadcastResponseDto();
      if (resp.isSuccess()) {
        dto = mapper.mapApiToDto(resp.getResult());
      } else { // fail
        dto.setCode(resp.getCode());
        dto.setName(HttpResponseStatus.valueOf(resp.getCode()).reasonPhrase());
        dto.setMessage(resp.getErrors().stream().map(ApiError::getMessage).collect(Collectors.toList()));
      }
      dto.setBroadcastRequest(smsRequest);
      return dto;
    }).onFailure(M360ApiException.class).recoverWithItem(ex -> {
      throw new ApiSvcEx(ex.getMessage(), HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
    });
  }

}
