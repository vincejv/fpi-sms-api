package com.abavilla.fpi.util;

import io.vertx.core.http.HttpServerRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HttpUtil {
  public static String getUserAgent(HttpServerRequest request) {
    return request.getHeader("User-Agent");
  }

  private static final List<String> IP_HEADERS = Arrays.asList("HTTP_CF_CONNECTING_IP", "REMOTE_ADDR",
      "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
      "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "True-Client-IP");

  public static String getClientIpAddr(HttpServerRequest request) {
    return IP_HEADERS.stream()
        .map(request::getHeader)
        .filter(Objects::nonNull)
        .filter(ip -> !ip.isEmpty() && !ip.equalsIgnoreCase("unknown"))
        .findFirst()
        .orElse(request.remoteAddress().hostAddress());
  }
}
