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

package com.abavilla.fpi.entity.enums;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@RegisterForReflection
public enum ApiStatus {
  DEL(1, "Delivered"),
  UND(2, "Undelivered"),
  ACK(8, "Acknowledged"),
  REJ(16, "Rejected"),
  UNKNOWN(-1, ""),
  WAIT(-2, "Waiting");
  private static final Map<String,ApiStatus> ENUM_MAP = new HashMap<>();

  static {
    for(ApiStatus w : EnumSet.allOf(ApiStatus.class))
      ENUM_MAP.put(w.getValue(), w);
  }

  private final int id;
  @Accessors(chain = true)
  @Setter
  private String value;

  public static ApiStatus fromValue(String value) {
    return ENUM_MAP.getOrDefault(value, ApiStatus.UNKNOWN.setValue(value));
  }

  public static ApiStatus fromId(int id) {
    return ENUM_MAP.values().stream().filter(ApiStatus -> ApiStatus.getId() == id).findAny()
        .orElse(getDefaultValue().setValue(String.format("Unknown (%d)", id)));
  }

  /**
   * Used by the Mongo codec
   *
   * @return
   */
  public static ApiStatus getDefaultValue() {
    return UNKNOWN;
  }

  /**
   * Required to properly convert Java Enum name to value.
   * Value is used by front-end and usually uses <br>
   * 1. lowercase <br>
   * 2. dashes instead of underscores <br> <br>
   */
  @Override
  public String toString() {
    return this.value;
  }
}
