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

package com.abavilla.fpi.sms.config;

import com.abavilla.fpi.fw.config.BaseReflectionConfig;
import com.vincejv.m360.dto.ApiError;
import com.vincejv.m360.dto.ApiResponse;
import com.vincejv.m360.dto.BroadcastRequest;
import com.vincejv.m360.dto.BroadcastResponse;
import com.vincejv.m360.dto.ErrorResponse;
import com.vincejv.m360.dto.IApiRequest;
import com.vincejv.m360.dto.PageInfo;
import com.vincejv.m360.dto.SMSRequest;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {
  IApiRequest.class,
  ApiError.class,
  ApiResponse.class,
  BroadcastRequest.class,
  BroadcastResponse.class,
  ErrorResponse.class,
  PageInfo.class,
  SMSRequest.class,
})
public class ReflectionConfig extends BaseReflectionConfig {
}
