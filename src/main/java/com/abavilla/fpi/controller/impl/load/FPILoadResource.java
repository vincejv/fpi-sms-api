package com.abavilla.fpi.controller.impl.load;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.api.gl.load.RewardsReqDto;
import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.service.impl.load.RewardsSvc;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/fpi/load/reload")
public class FPILoadResource extends AbsResource<RewardsReqDto, RewardsTransStatus, RewardsSvc> {
  @POST
  public Uni<Response> loadUp(LoadReqDto loadReq) {
    return service.reloadNumber(loadReq);
  }
}
