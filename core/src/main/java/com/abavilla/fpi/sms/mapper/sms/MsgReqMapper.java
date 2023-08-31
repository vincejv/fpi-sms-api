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

package com.abavilla.fpi.sms.mapper.sms;

import com.abavilla.fpi.fw.entity.mongo.AbsMongoField;
import com.abavilla.fpi.fw.mapper.IDtoToEntityMapper;
import com.abavilla.fpi.sms.dto.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.sms.entity.sms.BroadcastRequestEntity;
import com.abavilla.fpi.sms.entity.sms.MsgReq;
import com.abavilla.fpi.sms.ext.dto.MsgReqDto;
import com.abavilla.fpi.telco.ext.enums.Telco;
import com.vincejv.m360.dto.ApiRequest;
import com.vincejv.m360.dto.BroadcastRequest;
import com.vincejv.m360.dto.SMSRequest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class MsgReqMapper implements IDtoToEntityMapper<MsgReqDto, MsgReq> {

  @ConfigProperty(name = "ph.com.m360.sender-id")
  String senderId;

  public abstract MsgReq mapFromResponse(BroadcastResponseDto broadcastResponseDto);

  AbsMongoField mapApiReqToBroadcastReqEntity(ApiRequest request) {
    AbsMongoField entity = null;
    if (request instanceof BroadcastRequest req) {
      entity = mapBroadcastReqDtoToBroadcastReqEntity(req);
    } else if (request instanceof SMSRequest req) {
      entity = mapSMSReqToBroadcastReqEntity(req);
    }
    return entity;
  }

  @Mapping(target = "senderId", expression = "java(senderId)")
  abstract BroadcastRequestEntity mapSMSReqToBroadcastReqEntity(SMSRequest smsRequest);


  @Mapping(target = "senderId", expression = "java(senderId)")
  abstract BroadcastRequestEntity mapBroadcastReqDtoToBroadcastReqEntity(BroadcastRequest smsRequest);

  @AfterMapping
  void afterMappingFromResponse(BroadcastResponseDto broadcastResponseDto, @MappingTarget MsgReq msgReq) {
    msgReq.setTelco(Telco.fromId(broadcastResponseDto.getTelcoId()));
  }

}
