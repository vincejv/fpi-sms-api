package com.abavilla.fpi.engine;

import com.abavilla.fpi.service.IProviderSvc;

public interface IEngine<Provider extends IProviderSvc, Basis> {

  Provider getProvider(Basis promo);
}
