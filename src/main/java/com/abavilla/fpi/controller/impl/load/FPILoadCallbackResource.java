package com.abavilla.fpi.controller.impl.load;

import com.abavilla.fpi.config.ApiKeyConfig;
import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.api.load.RewardsCallbackDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.service.impl.load.RewardsCallbackSvc;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/fpi/load")
public class FPILoadCallbackResource
    extends AbsResource<RewardsCallbackDto, RewardsTransStatus, RewardsCallbackSvc> {
  @Inject
  ApiKeyConfig apiKeyConfig;

  @Path("callback/{apiKey}")
  @POST
  public Uni<Void> callback(@PathParam("apiKey") String apiKey,
                            RewardsCallbackDto body) {
    if (StringUtils.equals(apiKey, apiKeyConfig.getGenericApiKey())) {
      return service.storeCallback(body);
    } else {
      throw new WebApplicationException(Response
          .status(HttpResponseStatus.UNAUTHORIZED.code())
          .build());
    }
  }
}
