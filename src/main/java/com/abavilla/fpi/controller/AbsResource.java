package com.abavilla.fpi.controller;

import com.abavilla.fpi.dto.IDto;
import com.abavilla.fpi.entity.AbsItem;
import com.abavilla.fpi.service.AbsSvc;
import io.smallrye.mutiny.Multi;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class AbsResource<E extends IDto, I extends AbsItem, S extends AbsSvc<E,I>> implements IResource<I> {
  @Inject
  protected S service;

  @GET
  public Multi<E> getAll() {
    return service.list();
  }

  @Override
  public I save() {
    return null;
  }
}
