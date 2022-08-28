package com.abavilla.fpi.entity.impl.sms;

import com.abavilla.fpi.entity.MongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MongoEntity(collection="app_error_log")
public class ErrorLog extends MongoItem {
  @BsonProperty("Message")
  private String message;
  @BsonProperty("StackTrace")
  private String stackTrace;
  @BsonProperty("Payload")
  private Object payload;
  @BsonProperty("DateCreated")
  private LocalDateTime dateCreated;
  @BsonProperty("DateUpdated")
  private LocalDateTime dateUpdated;
}
