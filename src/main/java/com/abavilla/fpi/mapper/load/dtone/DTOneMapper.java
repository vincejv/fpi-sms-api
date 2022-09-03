package com.abavilla.fpi.mapper.load.dtone;

import com.abavilla.fpi.entity.impl.dtone.DVSReq;
import com.abavilla.fpi.entity.impl.dtone.DVSResp;
import com.dtone.dvs.dto.TransactionRequest;
import com.dtone.dvs.dto.TransactionResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.FIELD)
public interface DTOneMapper {

  DVSReq mapDTOneReqToEntity(TransactionRequest dto);

  DVSResp mapDTOneRespToEntity(TransactionResponse dto);

  default String dtLdtToStr(LocalDateTime ldtTimestamp) {
    if (ldtTimestamp != null) {
      var formatter = DateTimeFormatter.ISO_DATE_TIME;
      return ZonedDateTime.of(ldtTimestamp, ZoneId.of("UTC")).format(formatter);
    } else {
      return null;
    }
  }

  default LocalDateTime dtStrToLdt(String strTimestamp) {
    var formatter = DateTimeFormatter.ISO_DATE_TIME;
    try {
      var zdt = ZonedDateTime.parse(strTimestamp, formatter);
      return zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    } catch (DateTimeException | NullPointerException ex) {
      return null;
    }
  }
}