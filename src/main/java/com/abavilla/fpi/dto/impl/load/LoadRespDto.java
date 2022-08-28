package com.abavilla.fpi.dto.impl.load;

import com.abavilla.fpi.dto.AbsDto;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
public class LoadRespDto extends AbsDto {
  private String status;
  private String error;
  private String timestamp;
}
