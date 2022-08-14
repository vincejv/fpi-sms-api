package com.abavilla.fpi.service.impl;

import com.abavilla.fpi.dto.impl.MsgReqDto;
import com.abavilla.fpi.dto.impl.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.entity.impl.MsgReq;
import com.abavilla.fpi.mapper.MsgReqMapper;
import com.abavilla.fpi.service.AbsSvc;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MsgReqSvc extends AbsSvc<MsgReqDto, MsgReq> {
  @Inject
  MsgReqMapper mapper;
  @Inject
  M360Svc m360Svc;

  public Uni<BroadcastResponseDto> sendMsg(MsgReqDto msgReqDto) {
    return m360Svc.sendMsg(msgReqDto);
  }

  @Override
  public MsgReqDto mapToDto(MsgReq entity) {
    return mapper.mapToDto(entity);
  }

  @Override
  public MsgReq mapToEntity(MsgReqDto dto) {
    return mapper.mapToEntity(dto);
  }
}
