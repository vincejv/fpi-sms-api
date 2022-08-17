package com.abavilla.fpi.mapper;

import com.abavilla.fpi.dto.IDto;
import com.abavilla.fpi.entity.IItem;

public interface IMapper<DTO extends IDto, ENTITY extends IItem> {
  DTO mapToDto(ENTITY entity);
  ENTITY mapToEntity(DTO dto);
}
