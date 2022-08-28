package com.abavilla.fpi.mapper.sms;

import com.abavilla.fpi.dto.impl.sms.CustomerDto;
import com.abavilla.fpi.entity.impl.sms.Customer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD)
public interface CxMapper extends IMapper<CustomerDto, Customer> {
}
