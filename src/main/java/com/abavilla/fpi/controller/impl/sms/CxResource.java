package com.abavilla.fpi.controller.impl.sms;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.sms.CustomerDto;
import com.abavilla.fpi.entity.impl.sms.Customer;
import com.abavilla.fpi.service.impl.sms.CxSvc;

import javax.ws.rs.Path;

@Path("/fpi/cx")
public class CxResource extends AbsResource<CustomerDto, Customer, CxSvc> {

}