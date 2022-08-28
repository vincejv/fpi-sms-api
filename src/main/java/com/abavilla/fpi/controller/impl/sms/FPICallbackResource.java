package com.abavilla.fpi.controller.impl.sms;

import com.abavilla.fpi.config.ApiKeyConfig;
import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.NullDto;
import com.abavilla.fpi.entity.impl.sms.LeakAck;
import com.abavilla.fpi.service.impl.MsgAckSvc;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/fpi/sms/dlr")
public class FPICallbackResource extends AbsResource<NullDto, LeakAck, MsgAckSvc> {
  @Inject
  ApiKeyConfig apiKeyConfig;
  @Path("webhook/{apiKey}")
  @GET
  public Uni<Void> acknowledge(@QueryParam("status_code") String stsCde,
                               @QueryParam("transid") String msgId,
                               @QueryParam("timestamp") String timestamp,
                               @PathParam("apiKey") String apiKey) {
    if (StringUtils.equals(apiKey, apiKeyConfig.getDlrApiKey())) {
      return service.acknowledge(msgId, stsCde, timestamp);
    } else {
      throw new WebApplicationException(Response
          .status(HttpResponseStatus.UNAUTHORIZED.code())
          .build());
    }
  }
}
