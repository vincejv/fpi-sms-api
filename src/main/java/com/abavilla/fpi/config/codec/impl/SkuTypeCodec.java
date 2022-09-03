package com.abavilla.fpi.config.codec.impl;

import com.abavilla.fpi.config.codec.AbsCodec;
import com.abavilla.fpi.entity.enums.SkuType;

public class SkuTypeCodec extends AbsCodec<SkuType> {

  public SkuTypeCodec() {
    super();
  }

  @Override
  public Class<SkuType> getEncoderClass() {
    return SkuType.class;
  }
}
