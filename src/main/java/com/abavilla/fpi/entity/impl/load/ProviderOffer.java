package com.abavilla.fpi.entity.impl.load;

import com.abavilla.fpi.entity.mongo.AbsMongoField;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@RegisterForReflection
@NoArgsConstructor
@AllArgsConstructor
public class ProviderOffer extends AbsMongoField {
  private String providerName;
  private BigDecimal walletCost;
  private String productCode;
}
