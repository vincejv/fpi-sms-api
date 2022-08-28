package com.abavilla.fpi.mapper.sms;

import com.abavilla.fpi.dto.impl.api.m360.BroadcastResponseDto;
import com.abavilla.fpi.dto.impl.sms.MsgReqDto;
import com.abavilla.fpi.entity.enums.Telco;
import com.abavilla.fpi.entity.impl.sms.MsgReq;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD, uses = BroadcastRequestMapper.class)
public interface MsgReqMapper extends IMapper<MsgReqDto, MsgReq> {
  MsgReq mapFromResponse(BroadcastResponseDto broadcastResponseDto);
  @AfterMapping
  default void afterMappingFromResponse(BroadcastResponseDto broadcastResponseDto, @MappingTarget MsgReq msgReq) {
    msgReq.setTelco(Telco.fromId(broadcastResponseDto.getTelcoId()));
  }
}
