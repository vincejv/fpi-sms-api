package com.abavilla.fpi.exceptions;

import com.abavilla.fpi.util.MapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ApiSvcEx extends RuntimeException {
  private JsonNode jsonResponse;
  private HttpResponseStatus httpResponseStatus;
  public ApiSvcEx(String message, int httpStatus, JsonNode jsonNode) {
    super(message);
    this.httpResponseStatus = HttpResponseStatus.valueOf(httpStatus);
    this.jsonResponse = jsonNode;
  }
  public ApiSvcEx(String message) {
    super(message);
  }

  public <T> T getJsonResponse(Class<T> clazz) {
    return MapperUtil.convertJsonToObj(jsonResponse, clazz);
  }
}
