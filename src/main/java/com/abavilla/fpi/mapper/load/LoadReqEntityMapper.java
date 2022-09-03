package com.abavilla.fpi.mapper.load;

import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.entity.enums.Telco;
import com.abavilla.fpi.entity.impl.load.LoadReq;
import com.abavilla.fpi.mapper.sms.IMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD)
public interface LoadReqEntityMapper extends IMapper<LoadReqDto, LoadReq> {
  default Telco strToTelco(String telcoStr) {
    return Telco.fromValue(telcoStr);
  }

//  @Override
//  @Mapping(source = "address", target = "mobile")
//  LoadReqDto mapToDto(RewardsReq rewardsReq);
//
//  @Override
//  @Mapping(source = "mobile", target = "address")
//  RewardsReq mapToEntity(LoadReqDto loadReqDto);
}
