package com.abavilla.fpi.dto.impl.sms;

import com.abavilla.fpi.entity.mongo.AbsMongoItem;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
public class LoginDto extends AbsMongoItem {
  private String username;
  private String password;
  private String remoteAddress;
  private String userAgent;
}
