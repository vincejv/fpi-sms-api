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

package com.abavilla.fpi.service.impl.sms;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.enterprise.context.ApplicationScoped;

import com.abavilla.fpi.dto.impl.api.m360.BroadcastRequestDto;
import com.abavilla.fpi.dto.impl.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.dto.impl.sms.MsgReqDto;
import com.abavilla.fpi.entity.enums.DCSCoding;
import com.abavilla.fpi.repo.impl.m360.M360ApiRepo;
import com.abavilla.fpi.service.AbsApiSvc;
import com.abavilla.fpi.util.DateUtil;
import com.abavilla.fpi.util.MapperUtil;
import com.abavilla.fpi.util.SMSUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.smallrye.mutiny.Uni;
import lombok.SneakyThrows;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class M360Svc extends AbsApiSvc<BroadcastRequestDto, BroadcastResponseDto, JsonNode> {
  @RestClient
  M360ApiRepo m360Api;

  @ConfigProperty(name = "ph.com.m360.api-secret")
  String apiSecret;

  @ConfigProperty(name = "ph.com.m360.api-key")
  String apiKey;

  @ConfigProperty(name = "ph.com.m360.sender-id")
  String senderId;

  public Uni<BroadcastResponseDto> sendMsg(MsgReqDto msgReqDto) {
    var bRquest = new BroadcastRequestDto();
    bRquest.setAppKey(apiKey);
    bRquest.setAppSecret(apiSecret);
    bRquest.setMobileNumber(msgReqDto.getMobileNumber());
    bRquest.setContent(msgReqDto.getContent());
    bRquest.setSenderId(senderId);
    bRquest.setDataCodingScheme(SMSUtil.isEncodeableInGsm0338(msgReqDto.getContent()) ?
        DCSCoding.GSM0338.getId() : DCSCoding.UCS2.getId());

    Uni<JsonNode> m360Resp = m360Api.sendMsg(bRquest);
    return m360Resp.map(resp -> {
      BroadcastResponseDto dto = mapResponseToDto(resp);
      dto.setBroadcastRequest(bRquest);
      return dto;
    });
  }

  @SneakyThrows
  @Override
  public BroadcastResponseDto mapResponseToDto(JsonNode resp) {
    var bResp = new BroadcastResponseDto();
    bResp.setCode(resp.get("code").asInt());
    bResp.setName(resp.get("name").asText());
    if (resp.has("transid"))
      bResp.setTransId(resp.get("transid").asText());
    if (resp.has("timestamp"))
      bResp.setTimestamp(DateUtil.convertLdtUTC8ToUtc(LocalDateTime.parse(resp.get("timestamp").asText(),
          DateTimeFormatter.ofPattern(DateUtil.M360_TIMESTAMP_FORMAT))));
    if (resp.has("msgcount"))
      bResp.setMsgCount(resp.get("msgcount").asInt());

    if (resp.has("telco_id"))
      bResp.setTelcoId(resp.get("telco_id").asInt());

    if (resp.has("messageId"))
      bResp.setMessageId(resp.get("messageId").asText());
    if(resp.has("message"))
      bResp.setMessage(MapperUtil.mapper().readerForListOf(String.class).readValue(resp.get("message")));

    return bResp;
  }

  @Override
  public JsonNode mapToEntity(BroadcastRequestDto dto) {
    return null;
  }
}
