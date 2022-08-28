package com.abavilla.fpi.entity.impl.load;

import com.abavilla.fpi.entity.AbsFieldItem;
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
public class RewardsReq extends AbsFieldItem {
  private String address;
  private String sku;
}
