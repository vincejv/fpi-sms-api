package com.abavilla.fpi.entity.impl;

import com.abavilla.fpi.entity.MongoItem;
import com.abavilla.fpi.entity.enums.ApiStatus;
import com.abavilla.fpi.entity.enums.Telco;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MongoEntity(collection="m360_log")
public class MsgReq extends MongoItem {
  private String name;
  @BsonProperty("transid")
  private String transId;
  private LocalDateTime timestamp;
  private LocalDateTime ackTimestamp;
  @BsonProperty("msgcount")
  private Integer msgCount;
  @BsonProperty("telco")
  private Telco telco;
  private String messageId;
  private ApiStatus apiStatus;
  @BsonProperty("request")
  private BroadcastRequest broadcastRequest;
  private String test;
}
