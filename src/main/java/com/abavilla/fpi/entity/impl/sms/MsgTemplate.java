package com.abavilla.fpi.entity.impl.sms;

import com.abavilla.fpi.entity.mongo.AbsMongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MongoEntity(collection="sms_template")
public class MsgTemplate extends AbsMongoItem {
  private String name;
  private String content;
  private Boolean tokenRefresh;
}
