package com.abavilla.fpi.config.codec.impl;

import com.abavilla.fpi.config.codec.AbsCodec;
import com.abavilla.fpi.entity.enums.Telco;

public class TelcoCodec extends AbsCodec<Telco> {

  public TelcoCodec() {
    super();
  }

  @Override
  public Class<Telco> getEncoderClass() {
    return Telco.class;
  }
}
