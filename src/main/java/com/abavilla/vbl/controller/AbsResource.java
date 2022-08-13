package com.abavilla.vbl.controller;

import com.abavilla.vbl.model.AbsItem;
import com.abavilla.vbl.service.AbsService;

public abstract class AbsResource<T extends AbsItem, S extends AbsService> implements IResource<T> {
  S service;

  @Override
  public T get() {
    return null;
  }

  @Override
  public T save() {
    return null;
  }
}
