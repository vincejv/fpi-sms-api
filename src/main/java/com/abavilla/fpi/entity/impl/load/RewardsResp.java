package com.abavilla.fpi.entity.impl.load;

import com.abavilla.fpi.entity.AbsFieldItem;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@RegisterForReflection
@NoArgsConstructor
public class RewardsResp extends AbsFieldItem {
  private Integer transactionId;
  private String status;
  private String address;
  private String promo;
  private LocalDateTime timestamp;
  private String error;
}
