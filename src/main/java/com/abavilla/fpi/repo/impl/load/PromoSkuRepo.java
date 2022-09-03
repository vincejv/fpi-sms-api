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

package com.abavilla.fpi.repo.impl.load;

import com.abavilla.fpi.entity.enums.Telco;
import com.abavilla.fpi.entity.impl.load.PromoSku;
import com.abavilla.fpi.repo.AbsMongoRepo;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class PromoSkuRepo extends AbsMongoRepo<PromoSku> {
  public Uni<Optional<PromoSku>> findByTelcoAndKeyword(Telco telco, String keyword) {
    return find("telco = ?1 and keywords = ?2",
        telco.getValue(), keyword).firstResultOptional();
  }
}
