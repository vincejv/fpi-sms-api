package com.abavilla.fpi.service;

import com.abavilla.fpi.dto.IDto;
import com.abavilla.fpi.entity.AbsItem;
import com.abavilla.fpi.repo.IMongoRepo;
import io.smallrye.mutiny.Multi;

import javax.inject.Inject;

public abstract class AbsSvc<D extends IDto, I extends AbsItem> implements ISvc<D, I> {
  @Inject
  protected IMongoRepo<I> repo;

  public Multi<D > list() {
    return repo.streamAll().map(this::mapToDto);
  }
  public abstract D mapToDto(I entity);
  public abstract I mapToEntity(D dto);
}
