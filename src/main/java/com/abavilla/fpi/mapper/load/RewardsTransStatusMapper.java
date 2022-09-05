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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.inject.Inject;

import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsReqDto;
import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsRespDto;
import com.abavilla.fpi.dto.impl.load.LoadRespDto;
import com.abavilla.fpi.entity.enums.ApiStatus;
import com.abavilla.fpi.entity.impl.load.CallBack;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.entity.mongo.AbsMongoField;
import com.abavilla.fpi.mapper.load.dtone.DTOneMapper;
import com.abavilla.fpi.mapper.load.gl.GLMapper;
import com.dtone.dvs.dto.TransactionRequest;
import com.dtone.dvs.dto.TransactionResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class RewardsTransStatusMapper {

  @Inject
  DTOneMapper dtOneMapper;
  @Inject
  GLMapper glMapper;

  @Mappings(value = {
      @Mapping(target = "dateCreated", ignore = true),
      @Mapping(target = "dateUpdated", ignore = true),
      @Mapping(target = "apiCallback", source = "status")
  })
  public abstract void mapLoadRespDtoToEntity(LoadRespDto loadRespDto,
                              @MappingTarget RewardsTransStatus dest);

  /**
   * Adds initial callback
   * @return initial callback list
   */
  List<CallBack> addInitialCallbackStatus(ApiStatus apiStatus) {
    CallBack callBack = new CallBack();
    callBack.setStatus(apiStatus);
    callBack.setDateReceived(LocalDateTime.now(ZoneOffset.UTC));
    return List.of(callBack);
  }

  AbsMongoField anyObjectToAbsField(Object dto) {
    AbsMongoField field = null;
    if (dto instanceof GLRewardsReqDto) {
      field = glMapper.mapGLRewardsReqToEntity((GLRewardsReqDto)dto);
    } else if (dto instanceof GLRewardsRespDto) {
      field = glMapper.mapGLRewardsRespToEntity((GLRewardsRespDto) dto);
    } else if (dto instanceof TransactionRequest) {
      field = dtOneMapper.copyTransactionReqToDVSReq((TransactionRequest) dto);
    } else if (dto instanceof TransactionResponse) {
      field = dtOneMapper.copyTransactionRespToDVSResp((TransactionResponse) dto);
    }
    return field;
  }

}
