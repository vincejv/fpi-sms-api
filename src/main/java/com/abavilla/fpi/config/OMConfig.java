package com.abavilla.fpi.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.jackson.ObjectMapperCustomizer;

import javax.inject.Singleton;

@Singleton
public class OMConfig implements ObjectMapperCustomizer {
  @Override
  public void customize(ObjectMapper mapper) {
    final var originalSerConfig = mapper.getSerializationConfig();
    final var newSerConfig = originalSerConfig
        .with(MapperFeature.PROPAGATE_TRANSIENT_MARKER);
    mapper.setConfig(newSerConfig);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
