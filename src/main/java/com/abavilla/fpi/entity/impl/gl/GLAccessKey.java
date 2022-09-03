package com.abavilla.fpi.entity.impl.gl;

import com.abavilla.fpi.entity.mongo.AbsMongoItem;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
@NoArgsConstructor
public class GLAccessKey extends AbsMongoItem {
  private String accessToken;
}
