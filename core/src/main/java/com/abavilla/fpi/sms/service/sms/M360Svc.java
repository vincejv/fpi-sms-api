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

import com.abavilla.fpi.fw.rest.AbsApiSecSvc;
import com.abavilla.fpi.sms.dto.api.m360.BroadcastRequestDto;
import com.abavilla.fpi.sms.dto.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.sms.dto.api.m360.M360ResponseDto;
import com.abavilla.fpi.sms.entity.enums.DCSCoding;
import com.abavilla.fpi.sms.ext.dto.MsgReqDto;
import com.abavilla.fpi.sms.mapper.m360.BroadcastResponseMapper;
import com.abavilla.fpi.sms.rest.m360.M360ApiKeys;
import com.abavilla.fpi.sms.rest.m360.M360ApiRepo;
import com.abavilla.fpi.sms.util.SMSUtil;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class M360Svc extends AbsApiSecSvc<M360ApiRepo, M360ApiKeys> {

  @Inject
  BroadcastResponseMapper broadcastResponseMapper;

  public Uni<BroadcastResponseDto> sendMsg(MsgReqDto msgReqDto) {
    Log.debug("m360 message request: " + msgReqDto);
    var bRquest = new BroadcastRequestDto();
    bRquest.setAppKey(keys.getApiKey());
    bRquest.setAppSecret(keys.getApiSecret());
    bRquest.setMobileNumber(msgReqDto.getMobileNumber());
    bRquest.setContent(msgReqDto.getContent());
    bRquest.setSenderId(keys.getSenderId());
    bRquest.setDataCodingScheme(SMSUtil.isEncodeableInGsm0338(msgReqDto.getContent()) ?
        DCSCoding.GSM0338.getId() : DCSCoding.UCS2.getId());

    Uni<M360ResponseDto> m360Resp = client.sendMsg(bRquest);
    return m360Resp.map(resp -> {
      BroadcastResponseDto dto = mapResponseToDto(resp);
      dto.setBroadcastRequest(bRquest);
      return dto;
    });
  }

  public BroadcastResponseDto mapResponseToDto(M360ResponseDto apiResponse) {
    return broadcastResponseMapper.mapApiToDto(apiResponse);
  }
}
