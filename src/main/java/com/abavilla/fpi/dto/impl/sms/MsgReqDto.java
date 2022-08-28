package com.abavilla.fpi.dto.impl.sms;

import com.abavilla.fpi.dto.AbsDto;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
public class MsgReqDto extends AbsDto {
  private String content;
  private String mobileNumber;
}
