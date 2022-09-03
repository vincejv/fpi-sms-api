package com.abavilla.fpi.repo.impl.load.gl;

import com.abavilla.fpi.config.ApiRepoExHandler;
import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsReqDto;
import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsRespDto;
import com.abavilla.fpi.repo.IApiRepo;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;

@RegisterRestClient(configKey = "gl-rewards-api")
@RegisterProvider(value = ApiRepoExHandler.class)
public interface GLLoadApiRepo extends IApiRepo<GLRewardsReqDto> {
  @POST
  Uni<GLRewardsRespDto> sendLoad(GLRewardsReqDto req);
}
