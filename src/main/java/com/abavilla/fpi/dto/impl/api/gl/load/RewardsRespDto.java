package com.abavilla.fpi.dto.impl.api.gl.load;

import com.abavilla.fpi.dto.AbsDto;
import com.abavilla.fpi.dto.AbsFieldDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
public class RewardsRespDto extends AbsDto {
  @JsonProperty("outboundRewardRequest")
  private Body body;
  private String error;

  @Data
  @EqualsAndHashCode(callSuper = true)
  @NoArgsConstructor
  @RegisterForReflection
  public static class Body extends AbsFieldDto {
    @JsonProperty("transaction_id")
    private Integer transactionId;
    private String status;
    private String address;
    private String promo;
    private String timestamp;
  }
}
