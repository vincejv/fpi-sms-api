/*************************************************************************
 * FPI Application - Abavilla                                            *
 * Copyright (C) 2022  Vince Jerald Villamora                            *
 *                                                                       *
 * This program is free software: you can redistribute it and/or modify  *
 * it under the terms of the GNU General Public License as published by  *
 * the Free Software Foundation, either version 3 of the License, or     *
 * (at your option) any later version.                                   *
 *                                                                       *
 * This program is distributed in the hope that it will be useful,       *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 * GNU General Public License for more details.                          *
 *                                                                       *
 * You should have received a copy of the GNU General Public License     *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.*
 *************************************************************************/

package com.abavilla.fpi.sms.codec;

import java.lang.reflect.Method;

import com.vincejv.m360.dto.DCSCoding;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 * Codec for encoding and decoding {@link DCSCoding} enum to MongoDB Document
 *
 * @author <a href="mailto:vincevillamora@gmail.com">Vince Villamora</a>
 */
@NoArgsConstructor
public class DCSCodingCodec implements Codec<DCSCoding> {

  /**
   * Document key name for the value node
   */
  public static final String VALUE_KEY_NODE_NAME = "value";

  /**
   * Document key name for the enum id
   */
  public static final String ORD_KEY_NODE_NAME = "ord";

  /**
   * {@inheritDoc}
   */
  @SneakyThrows
  @Override
  public DCSCoding decode(BsonReader reader, DecoderContext decoderContext) {
    reader.readStartDocument();
    int ord = Integer.MIN_VALUE;
    while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
      // decode only value type, ignore ord
      String key = reader.readName();
      if (StringUtils.equals(key, VALUE_KEY_NODE_NAME)) {
        reader.readString();
      } else if (StringUtils.equals(key, ORD_KEY_NODE_NAME)) {
        ord = reader.readInt32();
      } else {
        reader.skipValue();
      }
    }
    reader.readEndDocument();

    Method fromValue = getEncoderClass().getDeclaredMethod("fromId", int.class);
    return (DCSCoding) fromValue.invoke(null, ord);
  }

  /**
   * {@inheritDoc}
   */
  @SneakyThrows
  @Override
  public void encode(BsonWriter writer, DCSCoding value, EncoderContext encoderContext) {
    if (value != null) {
      Method getId = getEncoderClass().getDeclaredMethod("getId");
      writer.writeStartDocument();
      writer.writeString(VALUE_KEY_NODE_NAME, value.toString());
      writer.writeInt32(ORD_KEY_NODE_NAME, (Integer) getId.invoke(value));
      writer.writeEndDocument();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<DCSCoding> getEncoderClass() {
    return DCSCoding.class;
  }

}
