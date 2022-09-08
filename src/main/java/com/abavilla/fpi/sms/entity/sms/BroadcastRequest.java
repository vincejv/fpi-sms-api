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

package com.abavilla.fpi.sms.entity.sms;

import com.abavilla.fpi.fw.entity.mongo.AbsMongoItem;
import com.abavilla.fpi.sms.entity.enums.DCSCoding;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
@AllArgsConstructor
public class BroadcastRequest extends AbsMongoItem {

  @BsonProperty("msisdn")
  private String mobileNumber;

  @BsonProperty("content")
  private String content;

  @BsonProperty("shortcode_mask")
  private String senderId;

  @BsonProperty("is_intl")
  private Boolean isInternational;

  @BsonProperty("dcs")
  private DCSCoding dataCodingScheme;

  public BroadcastRequest() {
    isInternational = false;
  }
}
