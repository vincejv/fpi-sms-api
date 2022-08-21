package com.abavilla.fpi.controller.impl;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.CustomerDto;
import com.abavilla.fpi.entity.impl.Customer;
import com.abavilla.fpi.service.impl.CxSvc;

import javax.ws.rs.Path;

@Path("/fpi/cx")
public class CxResource extends AbsResource<CustomerDto, Customer, CxSvc> {

}