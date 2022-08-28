package com.abavilla.fpi.mapper;

import com.abavilla.fpi.dto.impl.SessionDto;
import com.abavilla.fpi.entity.impl.sms.Session;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD)
public interface SessionMapper extends IMapper<SessionDto, Session> {
  @Mapping(target = "dateCreated", ignore = true)
  @Mapping(target = "dateUpdated", ignore = true)
  SessionDto mapToDto(Session entity);

  @Mapping(target = "dateCreated", ignore = true)
  @Mapping(target = "dateUpdated", ignore = true)
  Session mapToEntity(SessionDto dto);
}
