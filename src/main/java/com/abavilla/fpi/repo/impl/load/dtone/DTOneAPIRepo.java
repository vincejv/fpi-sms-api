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

package com.abavilla.fpi.repo.impl.load.dtone;

import com.dtone.dvs.DvsApiClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class DTOneAPIRepo {

  @ConfigProperty(name = "com.dtone.base-url")
  String baseUrl;

  @ConfigProperty(name = "com.dtone.api-key")
  String apiKey;

  @ConfigProperty(name = "com.dtone.api-secret")
  String apiSecret;

  @Produces
  DvsApiClient dvsApiClient() throws Exception {
    return new DvsApiClient(baseUrl, apiKey, apiSecret);
  }
}
