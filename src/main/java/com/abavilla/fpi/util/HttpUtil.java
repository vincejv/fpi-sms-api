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

package com.abavilla.fpi.util;

import io.vertx.core.http.HttpServerRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HttpUtil {
  public static String getUserAgent(HttpServerRequest request) {
    return request.getHeader("User-Agent");
  }

  private static final List<String> IP_HEADERS = Arrays.asList("HTTP_CF_CONNECTING_IP", "REMOTE_ADDR",
      "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
      "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "True-Client-IP");

  public static String getClientIpAddr(HttpServerRequest request) {
    return IP_HEADERS.stream()
        .map(request::getHeader)
        .filter(Objects::nonNull)
        .filter(ip -> !ip.isEmpty() && !ip.equalsIgnoreCase("unknown"))
        .findFirst()
        .orElse(request.remoteAddress().hostAddress());
  }
}
