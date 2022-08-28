package com.abavilla.fpi.entity;

import com.abavilla.fpi.util.MapperUtil;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class AbsFieldItem implements IItem {
  @Override
  public JsonNode toJson() {
      return MapperUtil.mapper().convertValue(this, JsonNode.class);
  }
}
