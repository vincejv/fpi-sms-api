package com.abavilla.fpi.mapper.gl.load;

import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.entity.impl.load.RewardsReq;
import com.abavilla.fpi.mapper.sms.IMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD)
public interface LoadReqMapper extends IMapper<LoadReqDto, RewardsReq> {

  @Override
  @Mapping(source = "address", target = "mobile")
  LoadReqDto mapToDto(RewardsReq rewardsReq);

  @Override
  @Mapping(source = "mobile", target = "address")
  RewardsReq mapToEntity(LoadReqDto loadReqDto);
}
