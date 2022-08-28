package com.abavilla.fpi.mapper.load;

import com.abavilla.fpi.dto.impl.api.load.RewardsCallbackDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.mapper.IMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD)
public interface RewardsTransMapper extends IMapper<RewardsCallbackDto.Body, RewardsTransStatus> {
  @Mapping(target = "timestamp") //, dateFormat = "EEE, MMM dd yyyy HH:mm:ss 'GMT' Z (z)")
  RewardsCallbackDto.Body mapToDto(RewardsTransStatus entity);

  @Mapping(target = "timestamp")//, dateFormat = "EEE, MMM dd yyyy HH:mm:ss 'GMT' Z (z)")
  RewardsTransStatus mapToEntity(RewardsCallbackDto.Body dto);

  default String ldtToStr(LocalDateTime ldtTimestamp) {
    var formatter = DateTimeFormatter
        .ofPattern("EEE, MMM dd yyyy HH:mm:ss 'GMT'Z (z)");
    return OffsetDateTime.of(ldtTimestamp, ZoneOffset.UTC).format(formatter);
  }

  default LocalDateTime strToLdt(String strTimestamp) {
    var formatter = DateTimeFormatter
        .ofPattern("EEE, MMM dd yyyy HH:mm:ss 'GMT'Z (z)");
    var odt = OffsetDateTime.parse(strTimestamp, formatter);
    return odt.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
  }
}
