package com.abavilla.fpi.mapper.load;

import com.abavilla.fpi.dto.impl.api.load.RewardsCallbackDto;
import com.abavilla.fpi.dto.impl.api.load.RewardsReqDto;
import com.abavilla.fpi.dto.impl.api.load.RewardsRespDto;
import com.abavilla.fpi.entity.impl.load.RewardsCallback;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.FIELD)
public interface RewardsReqMapper {

  @Mapping(source = "body.", target = "request.")
  void mapRequestDtoToEntity(RewardsReqDto dto,
                             @MappingTarget RewardsTransStatus rewardsTransStatus);

  @Mapping(source = "body.", target = "response.")
  @Mapping(source = "error", target = "response.error")
  void mapRespDtoToEntity(RewardsRespDto dto,
                          @MappingTarget RewardsTransStatus rewardsTransStatus);

  @Mapping(source = "body.", target = "callback.")
  void mapCallbackDtoToEntity(RewardsCallbackDto dto,
                              @MappingTarget RewardsTransStatus rewardsTransStatus);

  @Mapping(source = "body.", target = ".")
  RewardsCallback mapCallbackDtoToCallbackEntity(RewardsCallbackDto dto);

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
