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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.abavilla.fpi.fw.exceptions.ApiSvcEx;
import com.abavilla.fpi.fw.service.AbsSvc;
import com.abavilla.fpi.login.ext.entity.ServiceStatus;
import com.abavilla.fpi.login.ext.rest.UserApi;
import com.abavilla.fpi.sms.dto.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.sms.entity.sms.MsgReq;
import com.abavilla.fpi.sms.entity.sms.StateEncap;
import com.abavilla.fpi.sms.ext.dto.BulkMsgReqDto;
import com.abavilla.fpi.sms.ext.dto.MsgReqDto;
import com.abavilla.fpi.sms.ext.dto.MsgReqStatusDto;
import com.abavilla.fpi.sms.mapper.sms.MsgReqMapper;
import com.abavilla.fpi.sms.util.SMSConst;
import com.abavilla.fpi.telco.ext.enums.ApiStatus;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniJoin;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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

  @RestClient
  UserApi userApi;

  public Uni<MsgReqStatusDto> sendMsg(MsgReqDto msgReqDto) {
    var status = new MsgReqStatusDto();
    Log.debug("raw message request: " + msgReqDto);

    if (!validateAndFormatNumber(msgReqDto)) {
      status.setStatus(SMSConst.SMS_MOBILE_FORMAT_ERR); // consider adding a separate error code for invalid phone numbers
      return Uni.createFrom().item(status);
    }

    return
      m360Svc.sendMsg(msgReqDto)
        .onFailure().recoverWithItem(ex -> {  // | api failure
          Log.error("m360 api error: " + ex);
          var broadcastResponseDto = new BroadcastResponseDto();
          if (ex instanceof ApiSvcEx apiEx) {
            broadcastResponseDto.setCode(apiEx.getHttpResponseStatus().code());
            broadcastResponseDto.setName(apiEx.getHttpResponseStatus().reasonPhrase());
            broadcastResponseDto.setMessage(Collections.singletonList(apiEx.getMessage()));
          }
          return broadcastResponseDto;
        })
        .chain(respDto -> {
          var msgReq = msgReqMapper.mapFromResponse(respDto);
          msgReq.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
          msgReq.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
          var stateItem = new StateEncap(respDto.getCode() ==
            HttpResponseStatus.CREATED.code() ? ApiStatus.WAIT // success
            : ApiStatus.REJ, // api failure
            LocalDateTime.now(ZoneOffset.UTC));
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

  public Uni<MsgReqStatusDto> sendBulkMsg(BulkMsgReqDto bulkMsgReqDto) {
    var status = new MsgReqStatusDto();
    Log.debug("raw message request: " + bulkMsgReqDto);

    formatNumberList(bulkMsgReqDto);
    return filterNumberList(bulkMsgReqDto).chain(filteredReq -> {
      if (ObjectUtils.isEmpty(filteredReq.getMobileNumber())) {
        status.setStatus(SMSConst.SMS_MOBILE_FORMAT_ERR); // consider adding a separate error code for invalid phone numbers
        return Uni.createFrom().item(status);
      }
      // do not send messages to opted out users
      //var byMobile = userApi.getByMobile(filteredReq.getMobileNumber());
      UniJoin.Builder<MsgReqStatusDto> bulkSend = Uni.join().builder();

      for (String mobile : filteredReq.getMobileNumber()) {
        var msgReq = new MsgReqDto();
        msgReq.setMobileNumber(mobile);
        msgReq.setContent(filteredReq.getContent());

        var sendMsg = m360Svc.sendMsg(msgReq)
          .onFailure().recoverWithItem(ex -> {  // | api failure
            Log.error("m360 api error: " + ex);
            var apiResp = new BroadcastResponseDto();
            if (ex instanceof ApiSvcEx apiEx) {
              apiResp.setCode(apiEx.getHttpResponseStatus().code());
              apiResp.setName(apiEx.getHttpResponseStatus().reasonPhrase());
              apiResp.setMessage(Collections.singletonList(apiEx.getMessage()));
            }
            return apiResp;
          })
          .chain(apiResp -> {
            var resp = msgReqMapper.mapFromResponse(apiResp);
            resp.setDateCreated(LocalDateTime.now(ZoneOffset.UTC));
            resp.setDateUpdated(LocalDateTime.now(ZoneOffset.UTC));
            var stateItem = new StateEncap(apiResp.getCode() ==
              HttpResponseStatus.CREATED.code() ? ApiStatus.WAIT // success
              : ApiStatus.REJ, // api failure
              LocalDateTime.now(ZoneOffset.UTC));
            resp.setApiStatus(List.of(stateItem));
            return repo.persist(resp).onFailure().recoverWithNull(); // if failed to save to mongo, return null
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
        bulkSend = bulkSend.add(sendMsg);
      }

      return bulkSend.joinAll().andCollectFailures().map(resList -> {
        for (var sendSts : resList) {
          if (sendSts.getStatus() != SMSConst.SMS_SENT_SUCCESS) {
            return sendSts;
          }
        }
        return resList.get(0); // return first
      });
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

  /**
   * Checks the validity and formats the given phone number to the E164 format as accepted by backend SMS API.
   * @param bulkMsgDto {@link BulkMsgReqDto} Object containing phone number given, the formatted phone number will be
   *                  returned and stored in this object
   */
  @SneakyThrows
  private void formatNumberList(BulkMsgReqDto bulkMsgDto) {
    List<String> formattedList = new ArrayList<>(bulkMsgDto.getMobileNumber().size());
    for (String mobile : bulkMsgDto.getMobileNumber()) {
      var number = phoneNumberUtil.parse(mobile, SMSConst.PH_REGION_CODE);
      if (phoneNumberUtil.isValidNumber(number)) {
        formattedList.add(phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164));
      } else {
        Log.warn("Removed invalid phone number: " + mobile);
      }
    }
    bulkMsgDto.setMobileNumber(formattedList);
  }

  /**
   * Checks the validity and formats the given phone number to the E164 format as accepted by backend SMS API.
   * @param bulkMsgDto {@link BulkMsgReqDto} Object containing phone number given, the formatted phone number will be
   *                  returned and stored in this object
   */
  @SneakyThrows
  private Uni<BulkMsgReqDto> filterNumberList(BulkMsgReqDto bulkMsgDto) {
    List<Uni<String>> retrieveUsrOptIns = new ArrayList<>(bulkMsgDto.getMobileNumber().size());
    for (String mobile : bulkMsgDto.getMobileNumber()) {
      retrieveUsrOptIns.add(userApi.getByMobile(mobile).map(usr-> {
        if (usr.getResp().getSvcStatus() == ServiceStatus.OPT_IN) {
          return mobile;
        } else {
          return null; // skip opted out users
        }
      }).onFailure().recoverWithNull());
    }
    return Uni.join().all(retrieveUsrOptIns).andCollectFailures().map(mobs -> {
      var bulkMobileSend = mobs.stream().filter(Objects::nonNull).toList();
      bulkMsgDto.setMobileNumber(bulkMobileSend);
      return bulkMsgDto;
    });
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
