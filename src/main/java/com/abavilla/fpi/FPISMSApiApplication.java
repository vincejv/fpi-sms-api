package com.abavilla.fpi;

import com.abavilla.fpi.util.MapperUtil;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class FPISMSApiApplication {
  @Inject
  MapperUtil mapperUtil;
  void onStart(@Observes StartupEvent ev) {
    mapperUtil.init();
    Log.info("The application is starting...");
  }
}
