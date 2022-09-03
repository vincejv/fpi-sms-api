/******************************************************************************
 * FPI Application - Abavilla                                                 *
 * Copyright (C) 2022  Vince Jerald Villamora                                 *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.     *
 ******************************************************************************/

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
