package com.abavilla.fpi.dto.impl;

import com.abavilla.fpi.dto.AbsDto;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
public class MsgTemplateDto extends AbsDto {
  private String name;
  private String content;
  private Boolean tokenRefresh;
}
