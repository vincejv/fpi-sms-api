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

package com.abavilla.fpi.mapper.sms;

import com.abavilla.fpi.dto.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.dto.sms.MsgReqDto;
import com.abavilla.fpi.entity.enums.Telco;
import com.abavilla.fpi.entity.sms.MsgReq;
import com.abavilla.fpi.fw.mapper.IMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = BroadcastRequestMapper.class)
public interface MsgReqMapper extends IMapper<MsgReqDto, MsgReq> {
  MsgReq mapFromResponse(BroadcastResponseDto broadcastResponseDto);
  @AfterMapping
  default void afterMappingFromResponse(BroadcastResponseDto broadcastResponseDto, @MappingTarget MsgReq msgReq) {
    msgReq.setTelco(Telco.fromId(broadcastResponseDto.getTelcoId()));
  }
}
