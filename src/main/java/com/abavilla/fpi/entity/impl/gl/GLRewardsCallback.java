package com.abavilla.fpi.entity.impl.gl;

import com.abavilla.fpi.entity.mongo.AbsMongoItem;
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
public class GLRewardsCallback extends AbsMongoItem {
  private String status;
  private String sku;
  private LocalDateTime timestamp;
  private Integer transactionId;
  private String mobileNumber;
}
