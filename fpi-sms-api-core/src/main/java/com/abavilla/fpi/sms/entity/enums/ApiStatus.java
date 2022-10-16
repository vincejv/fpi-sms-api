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

package com.abavilla.fpi.sms.entity.enums;

import static com.abavilla.fpi.fw.util.FWConst.UNKNOWN_PREFIX;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.abavilla.fpi.fw.entity.enums.IBaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
@RegisterForReflection
public enum ApiStatus implements IBaseEnum {
  DEL(1, "Delivered"),
  UND(2, "Undelivered"),
  ACK(8, "Acknowledged"),
  REJ(16, "Rejected"),
  CREATED(200, "Created"),
  UNKNOWN(-1, ""),
  WAIT(-2, "Waiting");

  /**
   * Ordinal id to enum mapping
   */
  private static final Map<Integer, ApiStatus> ENUM_MAP = new HashMap<>();

  static {
    for(ApiStatus w : EnumSet.allOf(ApiStatus.class))
      ENUM_MAP.put(w.getId(), w);
  }

  /**
   * The enum ordinal id
   */
  private final int id;

  /**
   * The enum value
   */
  private String value;

  /**
   * Creates an enum based from given string value
   *
   * @param value the string value
   * @return the created enum
   */
  @JsonCreator
  public static ApiStatus fromValue(String value) {
    if (StringUtils.isBlank(value)) {
      return null;
    } else {
      return ENUM_MAP.values().stream().filter(enumItem ->
              StringUtils.equalsIgnoreCase(value, enumItem.getValue())).findAny()
          .orElseGet(() -> {
            var unknown = UNKNOWN;
            String enumValue = value;
            if (StringUtils.startsWithIgnoreCase(enumValue, UNKNOWN_PREFIX)) {
              enumValue = StringUtils.removeStart(enumValue, UNKNOWN_PREFIX);
            }
            unknown.value = UNKNOWN_PREFIX + enumValue;
            return unknown;
          });
    }
  }

  /**
   * Creates an enum based from given an ordinal id
   *
   * @param id the ordinal id
   * @return the created enum
   */
  public static ApiStatus fromId(int id) {
    return ENUM_MAP.values().stream().filter(enumItem ->
            id == enumItem.getId()).findAny()
        .orElseGet(() -> {
          var unknown = UNKNOWN;
          unknown.value = UNKNOWN_PREFIX + id;
          return unknown;
        });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @JsonValue
  public String toString() {
    return value;
  }
}