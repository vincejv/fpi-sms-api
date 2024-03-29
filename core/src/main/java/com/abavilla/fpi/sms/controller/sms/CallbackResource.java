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

package com.abavilla.fpi.sms.controller.sms;

import com.abavilla.fpi.fw.controller.AbsBaseResource;
import com.abavilla.fpi.fw.dto.impl.NullDto;
import com.abavilla.fpi.fw.dto.impl.RespDto;
import com.abavilla.fpi.fw.exceptions.FPISvcEx;
import com.abavilla.fpi.sms.config.ApiKeyConfig;
import com.abavilla.fpi.sms.entity.sms.LeakAck;
import com.abavilla.fpi.sms.service.sms.MsgAckSvc;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/fpi/sms/dlr")
public class CallbackResource
    extends AbsBaseResource<NullDto, LeakAck, MsgAckSvc> {

  @Inject
  ApiKeyConfig apiKeyConfig;

  @Path("webhook/{apiKey}")
  @GET
  public Uni<RespDto<NullDto>> acknowledge(@QueryParam("status_code") String stsCde,
                                           @QueryParam("transid") String msgId,
                                           @QueryParam("timestamp") String timestamp,
                                           @PathParam("apiKey") String apiKey) {
    if (StringUtils.equals(apiKey, apiKeyConfig.getDlrApiKey())) {
      return service.acknowledge(msgId, stsCde, timestamp);
    } else {
      throw new FPISvcEx("Unauthorized", RestResponse.StatusCode.UNAUTHORIZED);
    }
  }
}
