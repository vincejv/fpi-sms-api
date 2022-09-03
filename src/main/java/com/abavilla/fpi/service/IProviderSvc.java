package com.abavilla.fpi.service;

public interface IProviderSvc {
  void init();
  long getPriority();
  String getProviderName();
}
