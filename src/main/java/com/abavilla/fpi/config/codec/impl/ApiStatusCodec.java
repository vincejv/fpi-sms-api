package com.abavilla.fpi.config.codec.impl;

import com.abavilla.fpi.config.codec.AbsCodec;
import com.abavilla.fpi.entity.enums.ApiStatus;

public class ApiStatusCodec extends AbsCodec<ApiStatus> {

  public ApiStatusCodec() {
    super();
  }

  @Override
  public Class<ApiStatus> getEncoderClass() {
    return ApiStatus.class;
  }
}
