package com.abavilla.fpi.dto.impl.api.m360;

import com.abavilla.fpi.dto.AbsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@RegisterForReflection
@AllArgsConstructor
public class BroadcastRequestDto extends AbsDto {
  @JsonProperty("app_key")
  private String appKey;

  @JsonProperty("app_secret")
  private String appSecret;

  @JsonProperty("msisdn")
  private String mobileNumber;

  @JsonProperty("content")
  private String content;

  @JsonProperty("shortcode_mask")
  private String senderId;

  @JsonProperty("is_intl")
  private Boolean isInternational;

  @JsonProperty("dcs")
  private int dataCodingScheme;

  public BroadcastRequestDto() {
    isInternational = false;
    dataCodingScheme = 0;    /*
                             * 0 - SMSC Default Alphabet
                             * 1 - ASCII
                             * 3 - Latin 1 (ISO-8859-1)
                             * 8 - UCS2 (ISO/IEC-10646)
                             */
  }
}
