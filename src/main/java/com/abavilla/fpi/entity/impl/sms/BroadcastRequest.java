package com.abavilla.fpi.entity.impl.sms;

import com.abavilla.fpi.entity.enums.DCSCoding;
import com.abavilla.fpi.entity.mongo.AbsMongoItem;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
@AllArgsConstructor
public class BroadcastRequest extends AbsMongoItem {

  @BsonProperty("msisdn")
  private String mobileNumber;

  @BsonProperty("content")
  private String content;

  @BsonProperty("shortcode_mask")
  private String senderId;

  @BsonProperty("is_intl")
  private Boolean isInternational;

  @BsonProperty("dcs")
  private DCSCoding dataCodingScheme;

  public BroadcastRequest() {
    isInternational = false;
  }
}
