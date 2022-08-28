package com.abavilla.fpi.entity.impl.load;

import com.abavilla.fpi.entity.MongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
@MongoEntity(collection = "rewards_leak")
public class RewardsCallback extends MongoItem {
  private String status;
  private String sku;
  private LocalDateTime timestamp;
  private Integer transactionId;
  private String mobileNumber;
}
