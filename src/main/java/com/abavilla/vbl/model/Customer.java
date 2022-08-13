package com.abavilla.vbl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Customer extends MongoItem {
  public String name;
  public String address;
  public String mobile;
}
