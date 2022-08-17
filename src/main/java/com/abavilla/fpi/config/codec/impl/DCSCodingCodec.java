package com.abavilla.fpi.config.codec.impl;

import com.abavilla.fpi.config.codec.AbsCodec;
import com.abavilla.fpi.entity.enums.DCSCoding;

public class DCSCodingCodec extends AbsCodec<DCSCoding> {

  public DCSCodingCodec() {
    super();
  }

  @Override
  public Class<DCSCoding> getEncoderClass() {
    return DCSCoding.class;
  }
}
