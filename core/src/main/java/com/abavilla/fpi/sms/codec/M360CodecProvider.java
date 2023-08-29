/*************************************************************************
 * FPI Application - Abavilla                                            *
 * Copyright (C) 2023  Vince Jerald Villamora                            *
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

import java.util.ArrayList;
import java.util.List;

import com.vincejv.m360.dto.ApiRequest;
import com.vincejv.m360.dto.BroadcastRequest;
import com.vincejv.m360.dto.SMSRequest;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.ClassModelBuilder;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * MongoDB Codec registry, contains the codec for saving DVS API Objects to mongodb.
 *
 * @author <a href="mailto:vincevillamora@gmail.com">Vince Villamora</a>
 */
public class M360CodecProvider implements CodecProvider {

  private static final List<Class<?>> discriminatorClasses;

  private static final List<String> ignoredFields;

  static {
    discriminatorClasses = List.of(
      ApiRequest.class, BroadcastRequest.class, SMSRequest.class
    );

    ignoredFields = List.of("appKey", "appSecret");
  }

  @Override
  public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
    if (discriminatorClasses.contains(clazz)) {
      return buildDiscriminatorCodec(clazz, registry);
    }
    return null; // Don't throw here, this tells
  }

  private <T> Codec<T> buildDiscriminatorCodec(Class<T> clazz, CodecRegistry registry) {
    var discriminatorModelBuilder = ClassModel.builder(clazz)
      .enableDiscriminator(true);
    if (clazz == BroadcastRequest.class) {
      stripNonProperties(discriminatorModelBuilder);
    }
    return PojoCodecProvider.builder()
      .register(discriminatorModelBuilder.build())
      .build().get(clazz, registry);
  }

  private <T> void stripNonProperties(final ClassModelBuilder<T> builder) {
    List<String> names = new ArrayList<>();

    for (var property : builder.getPropertyModelBuilders()) {
      var name = property.getName();
      if (ignoredFields.contains(name)) {
        names.add(name);
      }
    }

    for (var name : names) {
      builder.removeProperty(name);
    }
  }

}
