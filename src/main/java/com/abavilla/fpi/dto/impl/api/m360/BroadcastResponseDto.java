package com.abavilla.fpi.dto.impl.api.m360;

import com.abavilla.fpi.dto.AbsDto;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
public class BroadcastResponseDto extends AbsDto {
  private Integer code;
  private String name;
  private String transId;
  private LocalDateTime timestamp;
  private int msgCount;
  private int telcoId;
  private String messageId;
}
