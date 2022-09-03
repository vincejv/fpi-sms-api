package com.abavilla.fpi.engine.load;

import com.abavilla.fpi.engine.AbsEngine;
import com.abavilla.fpi.entity.impl.load.PromoSku;
import com.abavilla.fpi.entity.impl.load.ProviderOffer;
import com.abavilla.fpi.service.impl.load.AbsLoadProviderSvc;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LoadEngine extends AbsEngine<AbsLoadProviderSvc, PromoSku> {
  @Override
  public AbsLoadProviderSvc getProvider(PromoSku promo) {
    final List<String> promoProviders = promo.getOffers()
        .stream()
        .sorted(Comparator.comparing(ProviderOffer::getWalletCost)) // sort list by wallet cost
        .map(ProviderOffer::getProviderName)
        .collect(Collectors.toUnmodifiableList());

    var provider = providers.stream()
        .filter(loadProvider ->
            promoProviders.stream()
                .anyMatch(name -> // only use provider which carry the promo
                    StringUtils.equals(name, loadProvider.getProviderName())
                )).min((o1, o2) -> {
                  int compare = // compare by provider wallet cost, if the same, use priority
                      Integer.compare(promoProviders.indexOf(o1.getProviderName()),
                          promoProviders.indexOf(o2.getProviderName()));
                  if (compare != 0) {
                    return compare;
                  } else {
                    return Long.compare(o1.getPriority(), o2.getPriority());
                  }
        });

    return provider.orElse(null);
  }
}
