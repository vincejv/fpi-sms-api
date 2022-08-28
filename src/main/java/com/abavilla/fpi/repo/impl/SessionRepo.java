package com.abavilla.fpi.repo.impl;

import com.abavilla.fpi.entity.impl.sms.Session;
import com.abavilla.fpi.repo.AbsMongoRepo;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class SessionRepo extends AbsMongoRepo<Session> {
  public Uni<Optional<Session>> findByUsername(String username){
    return find("username", username).firstResultOptional();
  }
}
