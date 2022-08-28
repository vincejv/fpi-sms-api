package com.abavilla.fpi.entity.impl.load;

import com.abavilla.fpi.entity.MongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@RegisterForReflection
@AllArgsConstructor
@MongoEntity(collection="rewards_log")
public class RewardsTransStatus extends MongoItem {
  private String status;
  private String sku;
  private LocalDateTime timestamp;
  private Integer transactionId;
  private String mobileNumber;
}
