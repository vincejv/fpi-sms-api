package com.abavilla.fpi.entity.mongo;

import com.abavilla.fpi.entity.IField;
import com.abavilla.fpi.util.MapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@Data
@EqualsAndHashCode(callSuper = false)
@RegisterForReflection
@NoArgsConstructor
@BsonDiscriminator
public abstract class AbsMongoField implements IField {
  @Override
  public JsonNode toJson() {
      return MapperUtil.mapper().convertValue(this, JsonNode.class);
  }
}
