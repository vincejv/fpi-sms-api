package com.abavilla.fpi.mapper;

import com.abavilla.fpi.dto.impl.MsgReqDto;
import com.abavilla.fpi.dto.impl.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.entity.enums.Telco;
import com.abavilla.fpi.entity.impl.sms.MsgReq;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD, uses = BroadcastRequestMapper.class)
public interface MsgReqMapper extends IMapper<MsgReqDto, MsgReq> {
  MsgReq mapFromResponse(BroadcastResponseDto broadcastResponseDto);
  @AfterMapping
  default void afterMappingFromResponse(BroadcastResponseDto broadcastResponseDto, @MappingTarget MsgReq msgReq) {
    msgReq.setTelco(Telco.fromId(broadcastResponseDto.getTelcoId()));
  }
}
