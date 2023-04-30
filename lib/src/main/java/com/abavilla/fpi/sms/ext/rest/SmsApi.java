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

package com.abavilla.fpi.sms.ext.rest;

import java.time.temporal.ChronoUnit;

import com.abavilla.fpi.fw.exceptions.AuthApiSvcEx;
import com.abavilla.fpi.fw.exceptions.handler.ApiRepoExHandler;
import com.abavilla.fpi.fw.rest.IApi;
import com.abavilla.fpi.login.ext.rest.AppToAppPreAuth;
import com.abavilla.fpi.sms.ext.dto.MsgReqDto;
import com.abavilla.fpi.sms.ext.dto.MsgReqStatusDto;
import io.smallrye.faulttolerance.api.ExponentialBackoff;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Resource access for authenticating with sending SMS through SMS service
 *
 * @author <a href="mailto:vincevillamora@gmail.com">Vince Villamora</a>
 */
@RegisterRestClient(configKey = "sms-api")
@RegisterProvider(value = ApiRepoExHandler.class)
@RegisterClientHeaders(AppToAppPreAuth.class)
@Retry(maxRetries = 8, retryOn = AuthApiSvcEx.class, delay = 3,
  delayUnit = ChronoUnit.SECONDS, jitter = 1500L)
@ExponentialBackoff(maxDelay = 25, maxDelayUnit = ChronoUnit.SECONDS)
public interface SmsApi extends IApi {

  /**
   * Send an SMS through SMS service
   * @param msgReqDto {@link MsgReqDto} object
   *
   * @return {@link MsgReqDto} future object containing the status
   */
  @POST
  Uni<MsgReqStatusDto> sendSms(MsgReqDto msgReqDto);
}
