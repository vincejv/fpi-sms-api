package com.abavilla.fpi.service.impl;

import com.abavilla.fpi.dto.impl.MsgReqDto;
import com.abavilla.fpi.dto.impl.api.m360.BroadcastRequestDto;
import com.abavilla.fpi.dto.impl.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.repo.impl.m360.M360ApiRepo;
import com.abavilla.fpi.service.AbsApiSvc;
import com.fasterxml.jackson.databind.JsonNode;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    Uni<JsonNode> m360Resp = m360Api.sendMsg(bRquest);
    return m360Resp.map(this::mapResponseToDto);
  }

  @Override
  public BroadcastResponseDto mapResponseToDto(JsonNode resp) {
    var bResp = new BroadcastResponseDto();
    bResp.setCode(resp.get("code").asInt());
    bResp.setName(resp.get("name").asText());
    bResp.setTransId(resp.get("transid").asText());
    bResp.setTimestamp(LocalDateTime.parse(resp.get("timestamp").asText(),
        DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    bResp.setMsgCount(resp.get("msgcount").asInt());
    bResp.setTelcoId(resp.get("telco_id").asInt());
    bResp.setMessageId(resp.get("messageId").asText());
    return bResp;
  }

  @Override
  public JsonNode mapToEntity(BroadcastRequestDto dto) {
    return null;
  }
}
