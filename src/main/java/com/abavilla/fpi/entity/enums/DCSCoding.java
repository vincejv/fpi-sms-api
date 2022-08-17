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
public enum DCSCoding {
  GSM0338(0, "GSM 03.38"),
  ASCII(1, "ASCII"),
  LATIN(3, "Latin 1 (ISO-8859-1)"),
  UCS2(8, "UCS2 (ISO/IEC-10646)"),
  UNKNOWN(-1, "");

  private static final Map<String, DCSCoding> ENUM_MAP = new HashMap<>();

  static {
    for(DCSCoding w : EnumSet.allOf(DCSCoding.class))
      ENUM_MAP.put(w.getValue(), w);
  }

  private final int id;
  @Accessors(chain = true)
  @Setter
  private String value;

  public static DCSCoding fromValue(String value) {
    return ENUM_MAP.getOrDefault(value, DCSCoding.UNKNOWN.setValue(value));
  }

  public static DCSCoding fromId(int id) {
    return ENUM_MAP.values().stream().filter(ApiStatus -> ApiStatus.getId() == id).findAny()
        .orElse(getDefaultValue().setValue(String.format("Unknown (%d)", id)));
  }

  /**
   * Used by the Mongo codec
   *
   * @return
   */
  public static DCSCoding getDefaultValue() {
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
