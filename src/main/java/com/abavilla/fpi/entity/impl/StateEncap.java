package com.abavilla.fpi.entity.impl;

import com.abavilla.fpi.entity.MongoItem;
import com.abavilla.fpi.entity.enums.ApiStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class StateEncap extends MongoItem implements Comparable<StateEncap> {
  private ApiStatus state;

  public StateEncap(ApiStatus state, LocalDateTime dateUpdated) {
    this.state = state;
    this.dateUpdated = (dateUpdated);
  }

  @Override
  public int compareTo(StateEncap other) {
    return Comparator.comparing(StateEncap::getDateCreated)
        .thenComparing(StateEncap::getState)
        .compare(this, other);
  }
}
