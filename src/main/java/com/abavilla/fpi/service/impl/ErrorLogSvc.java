package com.abavilla.fpi.service.impl;

import com.abavilla.fpi.dto.impl.ErrorLogDto;
import com.abavilla.fpi.entity.impl.ErrorLog;
import com.abavilla.fpi.service.AbsSvc;
import com.abavilla.fpi.util.MapperUtil;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ErrorLogSvc extends AbsSvc<ErrorLogDto, ErrorLog> {
  @Override
  public ErrorLogDto mapToDto(ErrorLog entity) {
    return MapperUtil.mapper().convertValue(entity, ErrorLogDto.class);
  }

  @Override
  public ErrorLog mapToEntity(ErrorLogDto dto) {
    return MapperUtil.mapper().convertValue(dto, ErrorLog.class);
  }
}
