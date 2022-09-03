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
