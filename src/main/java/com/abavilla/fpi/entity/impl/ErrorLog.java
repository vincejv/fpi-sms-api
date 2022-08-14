package com.abavilla.fpi.entity.impl;

import com.abavilla.fpi.entity.IItem;
import com.abavilla.fpi.entity.MongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MongoEntity(collection="app_error_log")
public class ErrorLog extends MongoItem {
  private String message;
  private String stackTrace;
  private IItem payload;
}
