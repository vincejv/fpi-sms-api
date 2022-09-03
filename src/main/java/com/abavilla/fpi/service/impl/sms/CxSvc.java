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

package com.abavilla.fpi.service.impl.sms;

import com.abavilla.fpi.dto.impl.sms.CustomerDto;
import com.abavilla.fpi.entity.impl.sms.Customer;
import com.abavilla.fpi.mapper.sms.CxMapper;
import com.abavilla.fpi.service.AbsSvc;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CxSvc extends AbsSvc<CustomerDto, Customer> {
  @Inject
  CxMapper mapper;

  @Override
  public CustomerDto mapToDto(Customer entity) {
    return mapper.mapToDto(entity);
  }

  @Override
  public Customer mapToEntity(CustomerDto dto) {
    return mapper.mapToEntity(dto);
  }
}
