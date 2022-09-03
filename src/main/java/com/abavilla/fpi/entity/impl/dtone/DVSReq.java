package com.abavilla.fpi.entity.impl.dtone;

import com.abavilla.fpi.entity.mongo.AbsMongoField;
import com.dtone.dvs.dto.CalculationMode;
import com.dtone.dvs.dto.Party;
import com.dtone.dvs.dto.PartyIdentifier;
import com.dtone.dvs.dto.Source;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@RegisterForReflection
@NoArgsConstructor
public class DVSReq extends AbsMongoField {
  private String externalId;
  private Long productId;
  private CalculationMode calculationMode;
  private Source source;
  private Source destination;
  private Boolean autoConfirm;
  private Party sender;
  private Party beneficiary;
  private PartyIdentifier debitPartyIdentifier;
  private PartyIdentifier creditPartyIdentifier;
  private String callbackUrl;
}
