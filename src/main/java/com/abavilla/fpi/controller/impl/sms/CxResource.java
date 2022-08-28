package com.abavilla.fpi.controller.impl.sms;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.CustomerDto;
import com.abavilla.fpi.entity.impl.sms.Customer;
import com.abavilla.fpi.service.impl.CxSvc;

import javax.ws.rs.Path;

@Path("/fpi/cx")
public class CxResource extends AbsResource<CustomerDto, Customer, CxSvc> {

}