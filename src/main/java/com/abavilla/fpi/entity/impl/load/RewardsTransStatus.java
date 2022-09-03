package com.abavilla.fpi.entity.impl.load;

import com.abavilla.fpi.entity.impl.gl.GLRewardsCallback;
import com.abavilla.fpi.entity.mongo.AbsMongoField;
import com.abavilla.fpi.entity.mongo.AbsMongoItem;
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
public class RewardsTransStatus extends AbsMongoItem {
  private LoadReq loadRequest;
  private GLRewardsCallback callback;
  private String loadProvider;
  private AbsMongoField apiRequest;
  private AbsMongoField apiResponse;
  private AbsMongoField apiCallback;
}
