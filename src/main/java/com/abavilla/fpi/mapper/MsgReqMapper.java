package com.abavilla.fpi.mapper;

import com.abavilla.fpi.dto.impl.MsgReqDto;
import com.abavilla.fpi.entity.impl.MsgReq;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD)
public interface MsgReqMapper {
  MsgReqDto mapToDto(MsgReq entity);
  MsgReq mapToEntity(MsgReqDto dto);
}
