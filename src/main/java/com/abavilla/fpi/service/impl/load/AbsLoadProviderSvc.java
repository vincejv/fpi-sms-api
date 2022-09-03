package com.abavilla.fpi.service.impl.load;

import com.abavilla.fpi.entity.impl.load.PromoSku;
import com.abavilla.fpi.entity.impl.load.ProviderOffer;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;

public abstract class AbsLoadProviderSvc implements ILoadProviderSvc {
  protected long priority;
  protected String providerName;

  @PostConstruct
  final void constructObject() {
    init();;
  }

  @Override
  public long getPriority() {
    return priority;
  }

  @Override
  public String getProviderName() {
    return providerName;
  }

  protected final String getProductCode(PromoSku promo) {
    return promo.getOffers().stream().filter(sku ->
        StringUtils.equals(sku.getProviderName(), providerName))
        .map(ProviderOffer::getProductCode).findAny().orElseThrow();
  }
}
