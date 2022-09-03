package com.abavilla.fpi.entity.impl.sms;

import com.abavilla.fpi.entity.enums.ApiStatus;
import com.abavilla.fpi.entity.mongo.AbsMongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MongoEntity(collection="m360_leak")
public class LeakAck extends AbsMongoItem {
  private String msgId;
  private ApiStatus apiStatus;
  private LocalDateTime timestamp;
}
