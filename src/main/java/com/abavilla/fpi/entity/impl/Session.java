package com.abavilla.fpi.entity.impl;

import com.abavilla.fpi.entity.MongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MongoEntity(collection="login_sessions")
public class Session extends MongoItem {
  private String username;
  private String refreshToken;
  private String accessToken;
  private String ipAddress;
  private String userAgent;
}
