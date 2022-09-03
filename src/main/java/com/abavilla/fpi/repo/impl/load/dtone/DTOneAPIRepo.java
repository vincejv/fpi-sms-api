package com.abavilla.fpi.repo.impl.load.dtone;

import com.dtone.dvs.DvsApiClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class DTOneAPIRepo {

  @ConfigProperty(name = "com.dtone.base-url")
  String baseUrl;

  @ConfigProperty(name = "com.dtone.api-key")
  String apiKey;

  @ConfigProperty(name = "com.dtone.api-secret")
  String apiSecret;

  @Produces
  DvsApiClient dvsApiClient() throws Exception {
    return new DvsApiClient(baseUrl, apiKey, apiSecret);
  }
}
