package com.abavilla.fpi.controller;

import com.abavilla.fpi.entity.AbsItem;

public interface IResource<I extends AbsItem> {
  I save();
}
