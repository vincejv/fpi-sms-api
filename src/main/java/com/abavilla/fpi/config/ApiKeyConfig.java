package com.abavilla.fpi.config;

import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Getter
public class ApiKeyConfig {
  @ConfigProperty(name = "fpi.webhook.dlr.webhook.api-key")
  String webhookKey;
}
