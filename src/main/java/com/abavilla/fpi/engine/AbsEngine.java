package com.abavilla.fpi.engine;

import com.abavilla.fpi.service.IProviderSvc;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

public abstract class AbsEngine<Provider extends IProviderSvc, Basis>
    implements IEngine<Provider, Basis> {
  @Inject
  protected Instance<Provider> providers;
}
