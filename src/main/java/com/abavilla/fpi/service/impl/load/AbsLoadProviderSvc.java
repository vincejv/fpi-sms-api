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
    init();
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
