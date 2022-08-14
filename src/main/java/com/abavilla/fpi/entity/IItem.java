package com.abavilla.fpi.entity;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public interface IItem extends Serializable {
  JsonNode toJson();
}
