package com.enigmacamp.minibookstore.service.imp;

import com.enigmacamp.minibookstore.repository.CustomersRepository;
import com.enigmacamp.minibookstore.service.CustomersService;
import model.dto.request.CustomerRequest;
import model.dto.response.CustomerResponse;
import model.entity.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomersService {

    private final CustomersRepository customersRepository;

    @Autowired
    public CustomerServiceImpl(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customers customer = new Customers();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customer.setAddress(customerRequest.getAddress());
        return CustomerResponse.fromEntity(customersRepository.insert(customer));
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return customersRepository.findById(id)
                .map(CustomerResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customersRepository.findAll().stream()
                .map(CustomerResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse updateCustomer(String id, CustomerRequest customerRequest) {
        Customers customer = customersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found" + id));
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customer.setAddress(customerRequest.getAddress());
        return CustomerResponse.fromEntity(customersRepository.update(customer));
    }

    @Override
    public void deleteCustomerById(String id) {
        customersRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found" + id));
        customersRepository.deleteById(id);
    }
}
