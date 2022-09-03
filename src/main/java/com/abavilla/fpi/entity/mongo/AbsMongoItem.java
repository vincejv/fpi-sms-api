package com.abavilla.fpi.entity.mongo;

import com.abavilla.fpi.entity.AbsItem;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.types.ObjectId;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
@BsonDiscriminator
public abstract class AbsMongoItem extends AbsItem {
  protected ObjectId id;
}
