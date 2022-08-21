package com.abavilla.fpi.config;

import lombok.Getter;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Getter
public class ApiKeyConfig {
  private String webhookKey;
  private String appKey;
}
