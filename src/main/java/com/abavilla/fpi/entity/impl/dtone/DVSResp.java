package com.abavilla.fpi.entity.impl.dtone;

import com.abavilla.fpi.entity.mongo.AbsMongoField;
import com.dtone.dvs.dto.Benefit;
import com.dtone.dvs.dto.Party;
import com.dtone.dvs.dto.PartyIdentifier;
import com.dtone.dvs.dto.Pin;
import com.dtone.dvs.dto.Prices;
import com.dtone.dvs.dto.Product;
import com.dtone.dvs.dto.Promotion;
import com.dtone.dvs.dto.Rates;
import com.dtone.dvs.dto.Status;
import com.dtone.dvs.dto.Values;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
@NoArgsConstructor
public class DVSResp extends AbsMongoField {
  private Long id;
  private String externalId;
  private LocalDateTime creationDate;
  private LocalDateTime confirmationExpirationDate;
  private LocalDateTime confirmationDate;
  private Status status;
  private String operatorReference;
  private Pin pin;
  private Product product;
  private Prices prices;
  private Rates rates;
  private List<Benefit> benefits;
  private List<Promotion> promotions;
  private Values requestedValues;
  private Values adjustedValues;
  private Party sender;
  private Party beneficiary;
  private PartyIdentifier debitPartyIdentifier;
  private PartyIdentifier creditPartyIdentifier;
}
