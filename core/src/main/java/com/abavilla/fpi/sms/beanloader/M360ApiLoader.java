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

package com.abavilla.fpi.sms.beanloader;

import com.abavilla.fpi.sms.rest.m360.M360ApiKeys;
import com.vincejv.m360.M360ApiClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Bean definition for {@link M360ApiClient}.
 *
 * @author <a href="mailto:vincevillamora@gmail.com">Vince Villamora</a>
 */
@ApplicationScoped
public class M360ApiLoader {

  @Inject
  M360ApiKeys keys;

  @ConfigProperty(name = "ph.com.m360.base-url")
  String baseUrl;

  /**
   * Creates a {@link M360ApiClient} instance using the default configurations
   *
   * @return {@link M360ApiClient} instance
   */
  @Produces
  M360ApiClient m360ApiClient() {
    return new M360ApiClient(baseUrl, keys.getApiKey(), keys.getApiSecret(), keys.getSenderId());
  }

}
