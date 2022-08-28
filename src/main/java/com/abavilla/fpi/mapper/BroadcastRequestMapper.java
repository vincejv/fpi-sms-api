package com.abavilla.fpi.mapper;

import com.abavilla.fpi.dto.impl.api.m360.BroadcastRequestDto;
import com.abavilla.fpi.entity.enums.DCSCoding;
import com.abavilla.fpi.entity.impl.sms.BroadcastRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD)
public interface BroadcastRequestMapper extends IMapper<BroadcastRequestDto, BroadcastRequest> {
  @Mapping(target = "dataCodingScheme")
  BroadcastRequestDto mapToDto(BroadcastRequest entity);

  @Mapping(target = "dataCodingScheme")
  BroadcastRequest mapToEntity(BroadcastRequestDto dto);

  default Integer dcsEnumToInt(DCSCoding dcs) {
    // Custom mapping here resulting in a Map<> map
    return dcs == null ? 0 : dcs.getId();
  }

  default DCSCoding intToDcsEnum(Integer value) {
    // Custom mapping here resulting in a Map<> map
    return DCSCoding.fromId(value);
  }
}
