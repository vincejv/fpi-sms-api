package com.abavilla.fpi.dto;

import com.abavilla.fpi.util.MapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public abstract class AbsFieldDto implements IDto {

  @Override
  public JsonNode toJson() {
    return MapperUtil.mapper().convertValue(this, JsonNode.class);
  }
}
