package com.enigmacamp.minibookstore.service;

import model.dto.request.CustomerRequest;
import model.dto.response.CustomerResponse;

import java.util.List;

public interface CustomersService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    CustomerResponse getCustomerById(String id);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse updateCustomer(String id, CustomerRequest customerRequest);
    void deleteCustomerById(String id);
}
