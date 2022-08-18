package com.abavilla.fpi.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
public abstract class MongoItem extends AbsItem {
  protected ObjectId id;
}
