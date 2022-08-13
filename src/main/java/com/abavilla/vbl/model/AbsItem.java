package com.abavilla.vbl.model;

import com.abavilla.vbl.util.MapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class AbsItem implements IItem {

  private LocalDateTime dateCreated;
  private LocalDateTime dateUpdated;

  @Override
  public JsonNode toJson() {
    return MapperUtil.mapper().convertValue(this, JsonNode.class);
  }
}
