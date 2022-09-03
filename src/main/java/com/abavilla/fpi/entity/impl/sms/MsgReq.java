package com.abavilla.fpi.entity.impl.sms;

import com.abavilla.fpi.entity.enums.Telco;
import com.abavilla.fpi.entity.mongo.AbsMongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MongoEntity(collection="m360_log")
public class MsgReq extends AbsMongoItem {
  private String name;
  @BsonProperty("transid")
  private String transId;
  private LocalDateTime timestamp;
  private LocalDateTime lastAcknowledgement;
  @BsonProperty("msgcount")
  private Integer msgCount;
  @BsonProperty("telco")
  private Telco telco;
  private String messageId;
  private List<StateEncap> apiStatus;
  @BsonProperty("request")
  private BroadcastRequest broadcastRequest;
}
