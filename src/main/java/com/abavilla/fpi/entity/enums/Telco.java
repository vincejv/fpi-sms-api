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
public enum Telco {
  GLOBE(1, "Globe"),
  SMART(2, "Smart"),
  SUN(3, "Sun"),
  DITO(4, "DITO"),
  UNKNOWN(-1, "");
  private static final Map<String,Telco> ENUM_MAP = new HashMap<>();

  static {
    for(Telco w : EnumSet.allOf(Telco.class))
      ENUM_MAP.put(w.getValue(), w);
  }

  private final int id;
  @Accessors(chain = true)
  @Setter
  private String value;

  public static Telco fromValue(String value) {
    return ENUM_MAP.getOrDefault(value, Telco.UNKNOWN.setValue(value));
  }

  public static Telco fromId(int id) {
    return ENUM_MAP.values().stream().filter(telco -> telco.getId() == id).findAny()
        .orElse(getDefaultValue().setValue(String.format("Unknown (%d)", id)));
  }

  /**
   * Used by the Mongo codec
   *
   * @return
   */
  public static Telco getDefaultValue() {
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
