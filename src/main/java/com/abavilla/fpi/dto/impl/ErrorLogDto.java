package com.abavilla.fpi.dto.impl;

import com.abavilla.fpi.dto.AbsDto;
import com.abavilla.fpi.entity.IItem;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
public class ErrorLogDto extends AbsDto {
  private String message;
  private String stackTrace;
  private IItem payload;
}
