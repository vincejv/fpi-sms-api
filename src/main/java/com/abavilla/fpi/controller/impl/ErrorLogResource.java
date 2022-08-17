package com.abavilla.fpi.controller.impl;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.ErrorLogDto;
import com.abavilla.fpi.entity.impl.ErrorLog;
import com.abavilla.fpi.service.impl.ErrorLogSvc;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.Path;

@Path("/log/error")
public class ErrorLogResource extends AbsResource<ErrorLogDto, ErrorLog, ErrorLogSvc> {
  public Uni<ErrorLogDto> post(ErrorLogDto dto) {
    return service.post(dto);
  }
}