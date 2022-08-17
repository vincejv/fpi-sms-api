package com.abavilla.fpi.repo.impl.m360;

import com.abavilla.fpi.config.ApiRepoExHandler;
import com.abavilla.fpi.dto.impl.api.m360.BroadcastRequestDto;
import com.abavilla.fpi.repo.IApiRepo;
import com.fasterxml.jackson.databind.JsonNode;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;

@RegisterRestClient(configKey = "m360-api")
@RegisterProvider(value = ApiRepoExHandler.class)
public interface M360ApiRepo extends IApiRepo<BroadcastRequestDto> {
  @POST
  Uni<JsonNode> sendMsg(BroadcastRequestDto broadcastRequestDto);
}
