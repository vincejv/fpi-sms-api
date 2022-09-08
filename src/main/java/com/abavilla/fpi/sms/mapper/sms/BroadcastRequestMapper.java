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

import com.abavilla.fpi.fw.mapper.IMapper;
import com.abavilla.fpi.sms.dto.api.m360.BroadcastRequestDto;
import com.abavilla.fpi.sms.entity.enums.DCSCoding;
import com.abavilla.fpi.sms.entity.sms.BroadcastRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BroadcastRequestMapper extends IMapper<BroadcastRequestDto, BroadcastRequest> {
  @Mapping(target = "dataCodingScheme")
  BroadcastRequestDto mapToDto(BroadcastRequest entity);

  @Mapping(target = "dataCodingScheme")
  BroadcastRequest mapToEntity(BroadcastRequestDto dto);

  default Integer dcsEnumToInt(DCSCoding dcs) {
    // Custom mapping here resulting in a Map<> map
    return dcs == null ? 0 : dcs.getId();
  }

  default DCSCoding intToDcsEnum(Integer value) {
    // Custom mapping here resulting in a Map<> map
    return DCSCoding.fromId(value);
  }
}
