package com.abavilla.fpi.config;

import com.abavilla.fpi.exceptions.ApiSvcEx;
import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.arc.Priority;
import lombok.SneakyThrows;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@Priority(Priorities.USER)
public class ApiRepoExHandler
    implements ResponseExceptionMapper<ApiSvcEx> {

  @Override
  public ApiSvcEx toThrowable(Response response) {
    try {
      response.bufferEntity();
    } catch (Exception ignored) {
    }
    return new ApiSvcEx("Rest Client encountered an exception!", response.getStatus(), getBody(response));
  }

  @Override
  public boolean handles(int status, MultivaluedMap<String, Object> headers) {
    return status >= 400;
  }

  @SneakyThrows
  private JsonNode getBody(Response response) {
    if (response.hasEntity()) {
      return response.readEntity(JsonNode.class);
    } else {
      return null;
    }
  }
}
