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

package com.abavilla.fpi.sms.mapper.m360;

import java.time.LocalDateTime;

import com.abavilla.fpi.fw.mapper.IMapper;
import com.abavilla.fpi.fw.util.DateUtil;
import com.abavilla.fpi.sms.dto.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.sms.dto.api.m360.M360ResponseDto;
import com.abavilla.fpi.sms.util.M360Const;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BroadcastResponseMapper extends IMapper {
  BroadcastResponseDto mapApiToDto(M360ResponseDto apiResponse);

  default LocalDateTime parseTimestamp(String timestamp) {
    return DateUtil.modLdtToUtc(DateUtil.parseStrDateToLdt(timestamp, M360Const.M360_TIMESTAMP_FORMAT));
  }
}
