package com.abavilla.fpi.config.codec;

import com.abavilla.fpi.config.codec.impl.ApiStatusCodec;
import com.abavilla.fpi.config.codec.impl.DCSCodingCodec;
import com.abavilla.fpi.config.codec.impl.SkuTypeCodec;
import com.abavilla.fpi.config.codec.impl.TelcoCodec;
import com.abavilla.fpi.entity.enums.ApiStatus;
import com.abavilla.fpi.entity.enums.DCSCoding;
import com.abavilla.fpi.entity.enums.SkuType;
import com.abavilla.fpi.entity.enums.Telco;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class EnumCodecProvider implements CodecProvider {
  @Override
  public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
    if (clazz == Telco.class) {
      return (Codec<T>) new TelcoCodec();
    } else if (clazz == ApiStatus.class) {
      return (Codec<T>) new ApiStatusCodec();
    } else if (clazz == DCSCoding.class) {
      return (Codec<T>) new DCSCodingCodec();
    } else if (clazz == SkuType.class) {
      return (Codec<T>) new SkuTypeCodec();
    }
    return null; // Don't throw here, this tells Mongo this provider doesn't provide a decoder for the requested clazz
  }
}
