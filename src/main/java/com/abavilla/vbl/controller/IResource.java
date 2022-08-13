package com.abavilla.vbl.controller;

import com.abavilla.vbl.model.AbsItem;

public interface IResource<T extends AbsItem> {
  T get();
  T save();
}
