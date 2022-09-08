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

package com.abavilla.fpi.sms.config.codec;

import com.abavilla.fpi.fw.config.codec.IEnumCodecProvider;
import com.abavilla.fpi.sms.config.codec.impl.ApiStatusCodec;
import com.abavilla.fpi.sms.config.codec.impl.DCSCodingCodec;
import com.abavilla.fpi.sms.config.codec.impl.TelcoCodec;
import com.abavilla.fpi.sms.entity.enums.ApiStatus;
import com.abavilla.fpi.sms.entity.enums.DCSCoding;
import com.abavilla.fpi.sms.entity.enums.Telco;
import org.bson.codecs.Codec;

public class EnumCodecProvider implements IEnumCodecProvider {
  @Override
  public <T> Codec<T> getCodecProvider(Class<T> clazz) {
    if (clazz == Telco.class) {
      return (Codec<T>) new TelcoCodec();
    } else if (clazz == ApiStatus.class) {
      return (Codec<T>) new ApiStatusCodec();
    } else if (clazz == DCSCoding.class) {
      return (Codec<T>) new DCSCodingCodec();
    }

    return null; // Don't throw here, this tells Mongo this provider doesn't provide a decoder for the requested clazz
  }
}
