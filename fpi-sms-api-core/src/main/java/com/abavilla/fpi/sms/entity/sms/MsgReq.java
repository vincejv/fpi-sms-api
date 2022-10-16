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

import java.time.LocalDateTime;
import java.util.List;

import com.abavilla.fpi.fw.entity.mongo.AbsMongoItem;
import com.abavilla.fpi.sms.entity.enums.Telco;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@BsonDiscriminator
@MongoEntity(collection="m360_log")
public class MsgReq extends AbsMongoItem {
  private String name;
  @BsonProperty("transid")
  private String transId;
  private LocalDateTime timestamp;
  private LocalDateTime lastAcknowledgement;
  @BsonProperty("msgcount")
  private Integer msgCount;
  @BsonProperty("telco")
  private Telco telco;
  private String messageId;
  private List<StateEncap> apiStatus;
  @BsonProperty("request")
  private BroadcastRequest broadcastRequest;
}
