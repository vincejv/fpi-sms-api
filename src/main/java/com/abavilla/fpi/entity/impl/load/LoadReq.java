package com.abavilla.fpi.entity.impl.load;

import com.abavilla.fpi.entity.enums.Telco;
import com.abavilla.fpi.entity.mongo.AbsMongoField;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
@NoArgsConstructor
public class LoadReq extends AbsMongoField {
  private String sku;
  private String mobile;
  private Telco telco;
}
