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
