package com.abavilla.fpi.repo.impl;

import com.abavilla.fpi.entity.impl.sms.Customer;
import com.abavilla.fpi.repo.AbsMongoRepo;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CxRepo extends AbsMongoRepo<Customer> {

}
