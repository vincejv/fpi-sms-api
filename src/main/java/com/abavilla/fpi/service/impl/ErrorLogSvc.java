package com.abavilla.fpi.service.impl;

import com.abavilla.fpi.dto.impl.ErrorLogDto;
import com.abavilla.fpi.entity.impl.sms.ErrorLog;
import com.abavilla.fpi.service.AbsSvc;
import com.abavilla.fpi.util.MapperUtil;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ErrorLogSvc extends AbsSvc<ErrorLogDto, ErrorLog> {
  public Uni<ErrorLogDto> post(ErrorLogDto dto) {
    return repo.persist(mapToEntity(dto)).map(this::mapToDto);
  }
  @Override
  public ErrorLogDto mapToDto(ErrorLog entity) {
    return MapperUtil.mapper().convertValue(entity, ErrorLogDto.class);
  }

  @Override
  public ErrorLog mapToEntity(ErrorLogDto dto) {
    return MapperUtil.mapper().convertValue(dto, ErrorLog.class);
  }
}
