/******************************************************************************
 * FPI Application - Abavilla                                                 *
 * Copyright (C) 2022  Vince Jerald Villamora                                 *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.     *
 ******************************************************************************/

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

  /**
   * 0 - SMSC Default Alphabet
   * 1 - ASCII
   * 3 - Latin 1 (ISO-8859-1)
   * 8 - UCS2 (ISO/IEC-10646)
   */
  @JsonProperty("dcs")
  private Integer dataCodingScheme;

  public BroadcastRequestDto() {
    isInternational = false;
  }
}
