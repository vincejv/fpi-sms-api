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

package com.abavilla.fpi.exceptions;

import com.abavilla.fpi.util.MapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ApiSvcEx extends RuntimeException {
  private JsonNode jsonResponse;
  private HttpResponseStatus httpResponseStatus;
  public ApiSvcEx(String message, int httpStatus, JsonNode jsonNode) {
    super(message);
    this.httpResponseStatus = HttpResponseStatus.valueOf(httpStatus);
    this.jsonResponse = jsonNode;
  }
  public ApiSvcEx(String message) {
    super(message);
  }

  public <T> T getJsonResponse(Class<T> clazz) {
    return MapperUtil.convert(jsonResponse, clazz);
  }
}
