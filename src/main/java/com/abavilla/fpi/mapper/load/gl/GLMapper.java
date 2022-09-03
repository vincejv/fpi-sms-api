package com.abavilla.fpi.mapper.load.gl;

import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsReqDto;
import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsRespDto;
import com.abavilla.fpi.entity.impl.gl.GLRewardsReq;
import com.abavilla.fpi.entity.impl.gl.GLRewardsResp;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.FIELD)
public interface GLMapper {
  @Mapping(source = "body.", target = ".")
  abstract GLRewardsReq mapGLRewardsReqToEntity(GLRewardsReqDto dto);
  @Mapping(source = "body.", target = ".")
  @Mapping(source = "error", target = "error")
  abstract GLRewardsResp mapGLRewardsRespToEntity(GLRewardsRespDto dto);

  default String glLdtToStr(LocalDateTime ldtTimestamp) {
    if (ldtTimestamp != null) {
      var formatter = DateTimeFormatter
          .ofPattern("EEE, MMM dd yyyy HH:mm:ss 'GMT'Z (z)");
      return ZonedDateTime.of(ldtTimestamp, ZoneId.of("UTC")).format(formatter);
    } else {
      return null;
    }
  }

  default LocalDateTime glStrToLdt(String strTimestamp) {
    var formatter = DateTimeFormatter
        .ofPattern("EEE, MMM dd yyyy HH:mm:ss 'GMT'Z (z)");
    try {
      var zdt = ZonedDateTime.parse(strTimestamp, formatter);
      return zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    } catch (DateTimeException | NullPointerException ex) {
      return null;
    }
  }
}
