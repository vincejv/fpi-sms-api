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
      return (T) method.invoke(null, value);
    }catch(Exception e) {
      try {
        String value = reader.readString();
        Method method = getEncoderClass().getDeclaredMethod("getDefaultValue");
        return (T) method.invoke(null, value);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      e.printStackTrace();
    }
    return null;
  }

  public abstract Class<T> getEncoderClass();
}
