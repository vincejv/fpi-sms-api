package com.abavilla.fpi.service;

import com.abavilla.fpi.dto.IDto;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class AbsApiSvc<D extends IDto, O extends IDto, I extends JsonNode> implements ISvc<D, I> {
  public abstract O mapResponseToDto(I entity);
  public abstract I mapToEntity(D dto);
}
