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

package com.abavilla.fpi.controller.impl.load;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsReqDto;
import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.repo.impl.load.PromoSkuRepo;
import com.abavilla.fpi.service.impl.load.RewardsSvc;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/fpi/load/reload")
public class FPILoadResource extends AbsResource<GLRewardsReqDto, RewardsTransStatus, RewardsSvc> {
  // Testonly
  @Inject
  PromoSkuRepo promoSkuRepo;

  @POST
  public Uni<Response> loadUp(LoadReqDto loadReq) {
//
//    PromoSku promoSku = new PromoSku();
//    promoSku.setType(SkuType.CREDITS);
//    promoSku.setSrp(NumberUtils.toScaledBigDecimal("100.00"));
//    promoSku.setName("Regular 100");
//
//    var offer1 = new ProviderOffer();
//    offer1.setProductCode("FPI100");
//    offer1.setWalletCost(NumberUtils.toScaledBigDecimal("95.45"));
//    offer1.setProviderName("GlobeLabs");
//
//    var offer2 = new ProviderOffer();
//    offer2.setProductCode("4749");
//    offer2.setWalletCost(NumberUtils.toScaledBigDecimal("98.00"));
//    offer2.setProviderName("DTOne");
//
//    promoSku.setOffers(List.of(offer1, offer2));
//
//    promoSku.setDenomination(NumberUtils.toScaledBigDecimal("100.00"));
//    promoSku.setTelco(Telco.GLOBE);
//
//    return promoSkuRepo.persist(promoSku).replaceWithVoid();
    return service.reloadNumber(loadReq);
  }
}
