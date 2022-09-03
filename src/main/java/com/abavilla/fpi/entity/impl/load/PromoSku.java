package com.abavilla.fpi.entity.impl.load;

import com.abavilla.fpi.entity.enums.SkuType;
import com.abavilla.fpi.entity.enums.Telco;
import com.abavilla.fpi.entity.mongo.AbsMongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
@NoArgsConstructor
@MongoEntity(collection = "promo_sku")
public class PromoSku extends AbsMongoItem {
  private SkuType type;
  private String name;
  private BigDecimal denomination;
  private BigDecimal srp;
  private Telco telco;
  private List<ProviderOffer> offers;
  private List<String> keywords;
}
