package com.abavilla.fpi.entity.impl.gl;

import com.abavilla.fpi.entity.mongo.AbsMongoField;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
@NoArgsConstructor
public class GLRewardsResp extends AbsMongoField {
  private Integer transactionId;
  private String status;
  private String address;
  private String promo;
  private LocalDateTime timestamp;
  private String error;
}
