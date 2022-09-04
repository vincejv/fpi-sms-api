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

package com.abavilla.fpi.service.impl.load.gl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsReqDto;
import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsRespDto;
import com.abavilla.fpi.dto.impl.load.LoadReqDto;
import com.abavilla.fpi.dto.impl.load.LoadRespDto;
import com.abavilla.fpi.entity.enums.ApiStatus;
import com.abavilla.fpi.entity.impl.load.PromoSku;
import com.abavilla.fpi.exceptions.ApiSvcEx;
import com.abavilla.fpi.mapper.load.LoadRespMapper;
import com.abavilla.fpi.repo.impl.load.gl.GLLoadApiRepo;
import com.abavilla.fpi.service.impl.load.AbsLoadProviderSvc;
import com.abavilla.fpi.util.AbavillaConst;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class GLRewardsSvc extends AbsLoadProviderSvc {

  @ConfigProperty(name = "ph.com.gl.app-id")
  String appId;

  @ConfigProperty(name = "ph.com.gl.app-secret")
  String appSecret;

  @ConfigProperty(name = "ph.com.gl.rewards.token")
  String amaxToken;

  @RestClient
  GLLoadApiRepo loadApi;

  @Inject
  LoadRespMapper rewardsMapper;

  @Override
  public void init() {
    priority = 0;
    providerName = AbavillaConst.PROV_GL;
  }

  @Override
  public Uni<LoadRespDto> reload(LoadReqDto req, PromoSku promo) {
    var apiReqBody = new GLRewardsReqDto.Body();
    apiReqBody.setAppId(appId);
    apiReqBody.setAppSecret(appSecret);
    apiReqBody.setRewardsToken(amaxToken);

    apiReqBody.setAddress(req.getMobile());
    apiReqBody.setSku(getProductCode(promo));

    var apiReq = new GLRewardsReqDto();
    apiReq.setBody(apiReqBody);
    var loadResp = new LoadRespDto();
    loadResp.setTransactionId(req.getTransactionId());
    loadResp.setApiRequest(apiReq);
    loadResp.setStatus(ApiStatus.CREATED);

    // rewardsMapper.mapRequestDtoToEntity(apiReq, log);
    return loadApi.sendLoad(apiReq)
        .onFailure(ApiSvcEx.class).recoverWithItem(ex -> {
          var apiEx = (ApiSvcEx) ex; // error encountered
          var resp = apiEx.getJsonResponse(GLRewardsRespDto.class);
          resp.chainEx(apiEx);
          return resp;
        })
        .map(resp -> {
          if (resp.getLastEx() == null) {
            loadResp.setStatus(ApiStatus.WAIT);
          }
          //rewardsMapper.mapGLRespDtoToEntity(resp, );
          rewardsMapper.mapGLRespToDto(resp, loadResp);
          loadResp.setApiResponse(resp);
          return loadResp;
          //var dtoResp = loadRespMapper.mapToDto(rewardsTransStatus.getResponse());
          //if (rewardsTransStatus.lastEx() == null) {
    //        return Response.ok().entity(dtoResp).build();
    //      } else {
    //        if (rewardsTransStatus.lastEx() instanceof ApiSvcEx) {
    //          return Response.status(((ApiSvcEx)rewardsTransStatus.lastEx())
    //              .getHttpResponseStatus().code()).entity(dtoResp).build();
    //        } else {
    //          return Response.serverError().entity(dtoResp).build();
    //        }
    //      }
        });
  }
}
