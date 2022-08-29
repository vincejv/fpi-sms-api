package com.abavilla.fpi.dto.impl.api.load.gl;

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
public class RewardsCallbackDto extends AbsDto {
  @JsonProperty("outboundRewardRequest")
  private Body body;

  @Data
  @EqualsAndHashCode(callSuper = true)
  @NoArgsConstructor
  @RegisterForReflection
  public static class Body extends AbsFieldDto {
    private String status;
    @JsonProperty("promo")
    private String sku;
    private String timestamp;
    @JsonProperty("transaction_id")
    private Integer transactionId;
    @JsonProperty("address")
    private String mobileNumber;
  }
}
