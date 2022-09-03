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
public class GLRewardsCallbackDto extends AbsDto {
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
