package com.abavilla.fpi.service.impl.load;

import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.dto.impl.load.LoadRespDto;
import com.abavilla.fpi.entity.impl.load.PromoSku;
import com.abavilla.fpi.service.IProviderSvc;
import io.smallrye.mutiny.Uni;

public interface ILoadProviderSvc extends IProviderSvc {
  Uni<LoadRespDto> reload(LoadReqDto req, PromoSku promo);
  default boolean isEnabled() {
    return true;
  }
}
