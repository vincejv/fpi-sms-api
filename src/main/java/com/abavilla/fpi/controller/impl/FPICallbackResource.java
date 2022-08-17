package com.abavilla.fpi.controller.impl;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.NullDto;
import com.abavilla.fpi.entity.impl.LeakAck;
import com.abavilla.fpi.service.impl.MsgAckSvc;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/fpi/sms/dlr")
public class FPICallbackResource extends AbsResource<NullDto, LeakAck, MsgAckSvc> {
  @Path("webhook.php")
  @GET
  public Uni<Void> acknowledge(@QueryParam("status_code") String stsCde,
                               @QueryParam("transid") String msgId,
                               @QueryParam("timestamp") String timestamp) {
    return service.acknowledge(msgId, stsCde, timestamp);
  }
}
