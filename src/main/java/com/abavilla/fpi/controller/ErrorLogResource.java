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

package com.abavilla.fpi.controller;

import javax.ws.rs.Path;

import com.abavilla.fpi.dto.ErrorLogDto;
import com.abavilla.fpi.entity.ErrorLog;
import com.abavilla.fpi.fw.controller.AbsResource;
import com.abavilla.fpi.service.ErrorLogSvc;
import io.smallrye.mutiny.Uni;

@Path("/fpi/log/error")
public class ErrorLogResource extends AbsResource<ErrorLogDto, ErrorLog, ErrorLogSvc> {
  public Uni<ErrorLogDto> post(ErrorLogDto dto) {
    return service.post(dto);
  }
}