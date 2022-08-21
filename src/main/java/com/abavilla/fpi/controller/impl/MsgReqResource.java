package com.abavilla.fpi.controller.impl;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.MsgReqDto;
import com.abavilla.fpi.dto.impl.MsgReqStatusDto;
import com.abavilla.fpi.entity.impl.MsgReq;
import com.abavilla.fpi.service.impl.MsgReqSvc;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/fpi/msg")
public class MsgReqResource extends AbsResource<MsgReqDto, MsgReq, MsgReqSvc> {

  @POST
  public Uni<MsgReqStatusDto> sendMsg(MsgReqDto msgReqDto) {
    return service.sendMsg(msgReqDto);
  }

  @Override
  public Multi<MsgReqDto> getAll() {
    throw new WebApplicationException(Response
        .status(HttpResponseStatus.NOT_FOUND.code())
        .build());
  }
}