package com.abavilla.fpi.entity.impl.load;

import com.abavilla.fpi.entity.MongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
@NoArgsConstructor
@MongoEntity(collection="rewards_log")
public class RewardsTransStatus extends MongoItem {
  private RewardsReq request;
  private RewardsResp response;
  private RewardsCallback callback;
}