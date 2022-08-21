package com.abavilla.fpi.dto.impl;

import com.abavilla.fpi.entity.MongoItem;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
public class LoginDto extends MongoItem {
  private String username;
  private String password;
}
