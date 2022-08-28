package com.abavilla.fpi.repo.impl.load;

import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.repo.AbsMongoRepo;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class RewardsTransRepo extends AbsMongoRepo<RewardsTransStatus> {
  public Uni<Optional<RewardsTransStatus>> findByRespTransId(Integer transId) {
    return find("response.transactionId", transId).firstResultOptional();
  }
}
