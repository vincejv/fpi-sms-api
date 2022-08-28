package com.abavilla.fpi.controller.impl.load;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.api.load.RewardsReqDto;
import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.dto.impl.load.LoadRespDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.service.impl.load.RewardsSvc;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/fpi/load/reload")
public class FPILoadResource extends AbsResource<RewardsReqDto, RewardsTransStatus, RewardsSvc> {
  @POST
  public Uni<LoadRespDto> loadUp(LoadReqDto loadReq) {
    return service.reloadNumber(loadReq);
  }
}
