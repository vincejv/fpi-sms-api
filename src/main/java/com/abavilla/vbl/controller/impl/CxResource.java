package com.abavilla.vbl.controller.impl;

import com.abavilla.vbl.controller.AbsResource;
import com.abavilla.vbl.model.impl.Customer;
import com.abavilla.vbl.service.impl.CxService;

import javax.ws.rs.Path;

@Path("/cx")
public class CxResource extends AbsResource<Customer, CxService> {
}