package com.abavilla.fpi.mapper.load;

import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsRespDto;
import com.abavilla.fpi.dto.impl.load.LoadRespDto;
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
public interface LoadRespMapper {

  @Mapping(target = "extTransactionId", source = "body.transactionId")
  @Mapping(target = "transactionId", ignore = true)
  @Mapping( target = ".", source = "body.")
  @Mapping(target = "dateCreated", ignore = true)
  @Mapping(target = "dateUpdated", ignore = true)
  void mapGLRespToDto(GLRewardsRespDto source,
                      @MappingTarget LoadRespDto dest);

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
