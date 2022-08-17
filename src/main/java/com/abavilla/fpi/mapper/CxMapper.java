package com.abavilla.fpi.mapper;

import com.abavilla.fpi.dto.impl.CustomerDto;
import com.abavilla.fpi.entity.impl.Customer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD)
public interface CxMapper extends IMapper<CustomerDto, Customer> {
}
