package com.abavilla.fpi.entity.impl.gl;

import com.abavilla.fpi.entity.mongo.AbsMongoField;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@RegisterForReflection
@NoArgsConstructor
@AllArgsConstructor
public class GLRewardsReq extends AbsMongoField {
  private String address;
  private String sku;
}
