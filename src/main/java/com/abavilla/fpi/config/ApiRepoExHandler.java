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

package com.abavilla.fpi.config;

import com.abavilla.fpi.exceptions.ApiSvcEx;
import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.arc.Priority;
import lombok.SneakyThrows;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@Priority(Priorities.USER)
public class ApiRepoExHandler
    implements ResponseExceptionMapper<ApiSvcEx> {

  @Override
  public ApiSvcEx toThrowable(Response response) {
    try {
      response.bufferEntity();
    } catch (Exception ignored) {
    }
    return new ApiSvcEx("Rest Client encountered an exception!", response.getStatus(), getBody(response));
  }

  @Override
  public boolean handles(int status, MultivaluedMap<String, Object> headers) {
    return status >= 400;
  }

  @SneakyThrows
  private JsonNode getBody(Response response) {
    if (response.hasEntity()) {
      return response.readEntity(JsonNode.class);
    } else {
      return null;
    }
  }
}
