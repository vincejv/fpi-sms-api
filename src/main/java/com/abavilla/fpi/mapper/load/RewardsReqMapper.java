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

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.FIELD)
public interface RewardsReqMapper {

  @Mapping(source = "body.", target = "request.")
  @Mapping(target = "dateCreated", ignore = true)
  @Mapping(target = "dateUpdated", ignore = true)
  void mapRequestDtoToEntity(RewardsReqDto dto,
                             @MappingTarget RewardsTransStatus rewardsTransStatus);

  @Mapping(source = "body.", target = "response.")
  @Mapping(source = "error", target = "response.error")
  @Mapping(target = "dateCreated", ignore = true)
  @Mapping(target = "dateUpdated", ignore = true)
  void mapRespDtoToEntity(RewardsRespDto dto,
                          @MappingTarget RewardsTransStatus rewardsTransStatus);

  @Mapping(source = "body.", target = "callback.")
  @Mapping(target = "dateCreated", ignore = true)
  @Mapping(target = "dateUpdated", ignore = true)
  void mapCallbackDtoToEntity(RewardsCallbackDto dto,
                              @MappingTarget RewardsTransStatus rewardsTransStatus);

  @Mapping(source = "body.", target = ".")
  @Mapping(target = "dateCreated", ignore = true)
  @Mapping(target = "dateUpdated", ignore = true)
  RewardsCallback mapCallbackDtoToCallbackEntity(RewardsCallbackDto dto);

  default String ldtToStr(LocalDateTime ldtTimestamp) {
    if (ldtTimestamp != null) {
      var formatter = DateTimeFormatter
          .ofPattern("EEE, MMM dd yyyy HH:mm:ss 'GMT'Z (z)");
      return ZonedDateTime.of(ldtTimestamp, ZoneId.of("UTC")).format(formatter);
    } else {
      return null;
    }
  }

  default LocalDateTime strToLdt(String strTimestamp) {
    var formatter = DateTimeFormatter
        .ofPattern("EEE, MMM dd yyyy HH:mm:ss 'GMT'Z (z)");
    try {
      var zdt = ZonedDateTime.parse(strTimestamp, formatter);
      return zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    } catch (DateTimeException ex) {
      return null;
    }
  }
}
