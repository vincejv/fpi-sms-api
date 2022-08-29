package com.abavilla.fpi.entity;

import com.abavilla.fpi.util.MapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RegisterForReflection
public abstract class AbsItem implements IItem {

  protected LocalDateTime dateCreated;

  protected LocalDateTime dateUpdated;

  private transient List<Exception> exceptions;

  public AbsItem() {
    exceptions = new ArrayList<>();
  }

  public void chainEx(Exception ex) {
    exceptions.add(ex);
  }

  public Exception lastEx() {
    if (exceptions.size() == 0) {
      return null;
    }
    return exceptions.get(exceptions.size()-1);
  }

  @Override
  public JsonNode toJson() {
    return MapperUtil.mapper().convertValue(this, JsonNode.class);
  }
}
