package com.abavilla.fpi.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@ApplicationScoped
public class MapperUtil {
  private static ObjectMapper mapper;
  @Inject
  ObjectMapper _mapper;

  public void init() {
    mapper = _mapper;
  }

  public static ObjectMapper mapper() {
    return mapper;
  }

  public static LocalDateTime convertLdtToUtc(LocalDateTime ldt, ZoneId zoneId) {
    var zdt = ZonedDateTime.of(ldt, zoneId);
    return zdt.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
  }

  public static LocalDateTime convertLdtUTC8ToUtc(LocalDateTime ldt) {
    var zdt = ZonedDateTime.of(ldt, ZoneId.of("Asia/Shanghai"));
    return zdt.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
  }

  public static <T> T convert(Object obj, Class<T> clazz) {
    return mapper.convertValue(obj, clazz);
  }
}
