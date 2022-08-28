package com.abavilla.fpi.controller.impl.sms;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.sms.ErrorLogDto;
import com.abavilla.fpi.entity.impl.sms.ErrorLog;
import com.abavilla.fpi.service.impl.sms.ErrorLogSvc;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.Path;

@Path("/fpi/log/error")
public class ErrorLogResource extends AbsResource<ErrorLogDto, ErrorLog, ErrorLogSvc> {
  public Uni<ErrorLogDto> post(ErrorLogDto dto) {
    return service.post(dto);
  }
}