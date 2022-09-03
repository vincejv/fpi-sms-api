package com.abavilla.fpi.service.impl.load;

import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.entity.enums.Telco;
import com.abavilla.fpi.entity.impl.load.PromoSku;
import com.abavilla.fpi.repo.impl.load.PromoSkuRepo;
import com.abavilla.fpi.service.AbsSvc;
import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.NotImplementedException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class PromoSkuSvc extends AbsSvc<LoadReqDto, PromoSku> {

  @Inject
  PromoSkuRepo advRepo;

  public Uni<Optional<PromoSku>> findSku(LoadReqDto loadReq) {
    return advRepo.findByTelcoAndKeyword(
        Telco.fromValue(loadReq.getTelco()), loadReq.getSku());
  }

  @Override
  public LoadReqDto mapToDto(PromoSku entity) {
    throw new NotImplementedException();
  }

  @Override
  public PromoSku mapToEntity(LoadReqDto dto) {
    throw new NotImplementedException();
  }
}
