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

package com.abavilla.fpi.controller.impl.load;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.abavilla.fpi.config.ApiKeyConfig;
import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.api.load.dtone.DVSCallbackDto;
import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsCallbackDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.service.impl.load.gl.RewardsCallbackSvc;
import com.abavilla.fpi.util.MapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.StringUtils;

@Path("/fpi/load")
public class FPILoadCallbackResource
    extends AbsResource<GLRewardsCallbackDto, RewardsTransStatus, RewardsCallbackSvc> {
  @Inject
  ApiKeyConfig apiKeyConfig;

  @Path("callback/{apiKey}")
  @POST
  public Uni<Void> callback(@PathParam("apiKey") String apiKey,
                            JsonNode body) {
    if (StringUtils.equals(apiKey, apiKeyConfig.getGenericApiKey())) {
      return service.storeCallback(MapperUtil.convert(body, GLRewardsCallbackDto.class));
    } else if (StringUtils.equals(apiKey, "intlprov")) {
      return service.storeCallback(MapperUtil.convert(body, DVSCallbackDto.class));
    } else {
      throw new WebApplicationException(Response
          .status(HttpResponseStatus.UNAUTHORIZED.code())
          .build());
    }
  }
}
