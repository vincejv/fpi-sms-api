package com.abavilla.fpi.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

@ApplicationScoped
public class MapperUtil {
  private static ObjectMapper mapper;
  @Inject
  private ObjectMapper _mapper;

  public void init() {
    mapper = _mapper;
  }

  public MapperUtil() {
    init();
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

  public static Map<String, ?> convertJsonNodeToMap(JsonNode jsonNode) {
    return mapper.convertValue(jsonNode, new TypeReference<Map<String, Object>>(){});
  }
}
