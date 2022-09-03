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
public class GLRewardsReqDto extends AbsDto {

  @JsonProperty("outboundRewardRequest")
  private Body body;

  @Data
  @EqualsAndHashCode(callSuper = true)
  @NoArgsConstructor
  @RegisterForReflection
  public static class Body extends AbsFieldDto {
    @JsonProperty("app_id")
    private String appId;
    @JsonProperty("app_secret")
    private String appSecret;
    @JsonProperty("rewards_token")
    private String rewardsToken;
    private String address;
    @JsonProperty("promo")
    private String sku;
  }
}
