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

package com.abavilla.fpi.sms.rest.m360;

import com.abavilla.fpi.fw.rest.IApiKeyConfig;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Data
@ApplicationScoped
public class M360ApiKeys implements IApiKeyConfig {

  @ConfigProperty(name = "ph.com.m360.api-secret")
  String apiSecret;

  @ConfigProperty(name = "ph.com.m360.api-key")
  String apiKey;

  @ConfigProperty(name = "ph.com.m360.sender-id")
  String senderId;

}
