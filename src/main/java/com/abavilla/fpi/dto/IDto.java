package com.abavilla.fpi.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public interface IDto extends Serializable {

  JsonNode toJson();
}
