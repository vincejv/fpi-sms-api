package com.abavilla.fpi.repo.impl.load.dtone;

import com.dtone.dvs.DvsApiClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class DTOneAPIRepo {
  @Produces
  DvsApiClient dvsApiClient() throws Exception {
    return new DvsApiClient(
        "https://preprod-dvs-api.dtone.com/v1/",
        "f5a9f35d-ef74-4840-8f7a-cacef7955d76",
        "a4f14d67-beac-4d98-b48d-bfc17c44d21c");
  }
}
