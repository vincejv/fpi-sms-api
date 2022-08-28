package com.abavilla.fpi.service.impl.sms;

import com.abavilla.fpi.dto.impl.sms.CustomerDto;
import com.abavilla.fpi.entity.impl.sms.Customer;
import com.abavilla.fpi.mapper.sms.CxMapper;
import com.abavilla.fpi.service.AbsSvc;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CxSvc extends AbsSvc<CustomerDto, Customer> {
  @Inject
  CxMapper mapper;

  @Override
  public CustomerDto mapToDto(Customer entity) {
    return mapper.mapToDto(entity);
  }

  @Override
  public Customer mapToEntity(CustomerDto dto) {
    return mapper.mapToEntity(dto);
  }
}
