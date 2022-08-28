package com.abavilla.fpi.controller.impl;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.LoginDto;
import com.abavilla.fpi.dto.impl.SessionDto;
import com.abavilla.fpi.entity.impl.sms.Session;
import com.abavilla.fpi.service.impl.LoginSvc;
import com.abavilla.fpi.util.HttpUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;
import org.apache.commons.lang3.BooleanUtils;
import org.jboss.resteasy.reactive.NoCache;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/fpi/login")
public class LoginResource extends AbsResource<SessionDto, Session, LoginSvc> {

  @Context
  HttpServerRequest request;

  @POST
  @NoCache
  public Uni<SessionDto> login(LoginDto loginDto,
                               @QueryParam("refreshToken")Boolean refreshToken){
    loginDto.setUserAgent(HttpUtil.getUserAgent(request));
    loginDto.setRemoteAddress(HttpUtil.getClientIpAddr(request));
    if (refreshToken == null || BooleanUtils.isFalse(refreshToken))
      return service.login(loginDto);
    else
      return service.refreshToken(loginDto);
  }

  @Override
  public Multi<SessionDto> getAll() {
    throw new WebApplicationException(Response
        .status(HttpResponseStatus.NOT_FOUND.code())
        .build());
  }

  @Override
  public Session save() {
    throw new WebApplicationException(Response
        .status(HttpResponseStatus.NOT_FOUND.code())
        .build());
  }
}