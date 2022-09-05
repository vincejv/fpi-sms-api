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

package com.abavilla.fpi.mapper.load;

import java.time.format.DateTimeFormatter;

import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsRespDto;
import com.abavilla.fpi.dto.impl.load.LoadRespDto;
import com.abavilla.fpi.mapper.load.gl.GLMapper;
import com.abavilla.fpi.util.DateUtil;
import com.dtone.dvs.dto.TransactionResponse;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LoadRespMapper {

  @Mappings(value = {
      @Mapping(target = "extTransactionId", source = "body.transactionId"),
      @Mapping(target = "transactionId", ignore = true),
      @Mapping(target = "status", ignore = true), // TODO: temporarily ignore
      @Mapping(target = "timestamp", source = "body.timestamp", qualifiedByName = "formatGlTimestamp"),
      @Mapping(target = ".", source = "body."),
      @Mapping(target = "dateCreated", ignore = true),
      @Mapping(target = "dateUpdated", ignore = true),
  })
  void mapGLRespToDto(GLRewardsRespDto source,
                      @MappingTarget LoadRespDto dest);
  @Mappings(value = {
      @Mapping(target = "extTransactionId", source = "id"),
      // @Mapping(target = "status", source = "status.message"),
      @Mapping(target = "timestamp", source = "creationDate"),
      @Mapping(target = "status", ignore = true),  // TODO: temporarily ignore
      @Mapping(target = "dateCreated", ignore = true),
      @Mapping(target = "dateUpdated", ignore = true),
  })
  void mapDTRespToDto(TransactionResponse source,
                      @MappingTarget LoadRespDto dest);

  @Named("formatGlTimestamp")
  default String formatGlTimestamp(String source) {
      if (StringUtils.isNotBlank(source)) {
        return DateUtil.convertStrDateToFormat(source,
            DateTimeFormatter.ofPattern(GLMapper.GL_TIMESTAMP_FORMAT),
            DateTimeFormatter.ISO_DATE_TIME);
      }
    return null;
  }

}
