package com.abavilla.fpi.repo.impl.load;

import com.abavilla.fpi.entity.enums.Telco;
import com.abavilla.fpi.entity.impl.load.PromoSku;
import com.abavilla.fpi.repo.AbsMongoRepo;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class PromoSkuRepo extends AbsMongoRepo<PromoSku> {
  public Uni<Optional<PromoSku>> findByTelcoAndKeyword(Telco telco, String keyword) {
    return find("telco = ?1 and keywords = ?2",
        telco.getValue(), keyword).firstResultOptional();
  }
}
