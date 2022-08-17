package com.abavilla.fpi.config.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.lang.reflect.Method;

public abstract class AbsCodec<T extends Enum<T>> implements Codec<T> {
  public AbsCodec() {
  }

  @Override
  final public void encode(final BsonWriter writer, final T value, final EncoderContext encoderContext) {
    String val = ((Enum) value).toString();
    writer.writeString(val);
  }

  @Override
  final public T decode(final BsonReader reader, final DecoderContext decoderContext) {
    try {
      String value = reader.readString();
      Method method = getEncoderClass().getDeclaredMethod("fromValue", String.class);
      T enumName = (T) method.invoke(null, value);
      return enumName;
    }catch(Exception e) {
      try {
        String value = reader.readString();
        Method method = getEncoderClass().getDeclaredMethod("getDefaultValue");
        T storageType = (T) method.invoke(null, value);
        return storageType;
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      e.printStackTrace();
    }
    return null;
  }

  public abstract Class<T> getEncoderClass();
}
