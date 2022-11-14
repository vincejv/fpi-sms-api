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

import com.abavilla.fpi.fw.mapper.IDtoToEntityMapper;
import com.abavilla.fpi.sms.dto.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.sms.entity.sms.MsgReq;
import com.abavilla.fpi.sms.ext.dto.MsgReqDto;
import com.abavilla.fpi.telco.ext.enums.Telco;
import org.bson.types.ObjectId;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = BroadcastRequestMapper.class)
public interface MsgReqMapper extends IDtoToEntityMapper<MsgReqDto, MsgReq> {

  @Override
  @Mappings(value = {
      @Mapping(target = "id", qualifiedByName = "MsgReqMapper")
  })
  MsgReqDto mapToDto(MsgReq msgReq);

  @Override
  @Mappings(value = {
      @Mapping(target = "id", qualifiedByName = "MsgReqMapper")
  })
  MsgReq mapToEntity(MsgReqDto dto);

  @Override
  @Named("MsgReqMapper")
  default ObjectId strToMongoId(String id) {
    return IDtoToEntityMapper.super.strToMongoId(id);
  }

  @Override
  @Named("MsgReqMapper")
  default String mongoIdToStr(ObjectId id) {
    return IDtoToEntityMapper.super.mongoIdToStr(id);
  }

  MsgReq mapFromResponse(BroadcastResponseDto broadcastResponseDto);
  @AfterMapping
  default void afterMappingFromResponse(BroadcastResponseDto broadcastResponseDto, @MappingTarget MsgReq msgReq) {
    msgReq.setTelco(Telco.fromId(broadcastResponseDto.getTelcoId()));
  }
}
