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

import com.abavilla.fpi.fw.exceptions.ApiSvcEx;
import com.abavilla.fpi.fw.service.AbsSvc;
import com.abavilla.fpi.sms.dto.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.sms.dto.api.m360.M360ResponseDto;
import com.abavilla.fpi.sms.entity.sms.MsgReq;
import com.abavilla.fpi.sms.entity.sms.StateEncap;
import com.abavilla.fpi.sms.ext.dto.MsgReqDto;
import com.abavilla.fpi.sms.ext.dto.MsgReqStatusDto;
import com.abavilla.fpi.sms.mapper.sms.MsgReqMapper;
import com.abavilla.fpi.sms.util.SMSConst;
import com.abavilla.fpi.telco.ext.enums.ApiStatus;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.SneakyThrows;

@ApplicationScoped
public class MsgReqSvc extends AbsSvc<MsgReqDto, MsgReq> {

  @Inject
  MsgReqMapper msgReqMapper;

  /**
   * Utility library for detecting and formatting phone numbers
   */
  @Inject
  PhoneNumberUtil phoneNumberUtil;

  @Inject
  M360Svc m360Svc;

  public Uni<MsgReqStatusDto> sendMsg(MsgReqDto msgReqDto) {
    var status = new MsgReqStatusDto();
    Log.debug("raw message request: " + msgReqDto);

    if (!validateAndFormatNumber(msgReqDto)) {
      status.setStatus(SMSConst.SMS_MOBILE_FORMAT_ERR); // consider adding a separate error code for invalid phone numbers
      return Uni.createFrom().item(status);
    }

    var broadcastResponseDtoUni = m360Svc.sendMsg(msgReqDto);

    return broadcastResponseDtoUni
      .onFailure().recoverWithItem(ex -> {  // | api failure
        Log.error("m360 api error: " + ex);
        var broadcastResponseDto = new BroadcastResponseDto();
        if (ex instanceof ApiSvcEx apiEx) {
          broadcastResponseDto = m360Svc.mapResponseToDto(apiEx.getJsonResponse(M360ResponseDto.class));
        }
        return broadcastResponseDto;
      })
      .chain(respDto -> {
        var msgReq = msgReqMapper.mapFromResponse(respDto);
        msgReq.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
        msgReq.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
        var stateItem = new StateEncap(ApiStatus.WAIT, LocalDateTime.now(ZoneOffset.UTC));
        msgReq.setApiStatus(List.of(stateItem));
        return repo.persist(msgReq).onFailure().recoverWithNull(); // if failed to save to mongo, return null
      })
      .map(respMongo -> {
        Log.debug("mongo save: " + respMongo);  // receive request from to save to mongo
        if (respMongo != null) {
          status.setStatus(respMongo
            .getName().equalsIgnoreCase("Created") ? SMSConst.SMS_SENT_SUCCESS :
            SMSConst.SMS_API_FAIL);  // if both mongo and api is success
        } else {
          status.setStatus(SMSConst.SMS_MONGO_FAIL); // mongo failure
        }
        return status;
      });
  }

  /**
   * Checks the validity and formats the given phone number to the E164 format as accepted by backend SMS API.
   * @param msgReqDto {@link MsgReqDto} Object containing phone number given, the formatted phone number will be
   *                  returned and stored in this object
   * @return {@code true} if phone number is valid and formatting was attempted, otherwise {@code false}
   */
  @SneakyThrows
  private boolean validateAndFormatNumber(MsgReqDto msgReqDto) {
    var number = phoneNumberUtil.parse(msgReqDto.getMobileNumber(), SMSConst.PH_REGION_CODE);
    if (!phoneNumberUtil.isValidNumber(number)) {
      return false;
    } else {
      msgReqDto.setMobileNumber(phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164));
    }
    return true;
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
