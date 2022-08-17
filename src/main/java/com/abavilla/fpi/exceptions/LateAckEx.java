package com.abavilla.fpi.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class LateAckEx extends RuntimeException {
  public LateAckEx(String message) {
    super(message);
  }
}
