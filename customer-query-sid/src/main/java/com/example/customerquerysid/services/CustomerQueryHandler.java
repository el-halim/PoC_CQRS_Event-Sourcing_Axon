package com.example.customerquerysid.services;


import com.example.customerquerysid.entites.Customer;
import com.example.customerquerysid.respositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.sid.coreapi.query.GetAllCustomersQuery;
import org.sid.coreapi.query.GetCustomerByIdQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerQueryHandler {

    private CustomerRepository customerRepository;


    @QueryHandler
    public List<Customer> customerList(GetAllCustomersQuery query){
        return customerRepository.findAll();
    }


    @QueryHandler
    public Customer getCustomer(GetCustomerByIdQuery query){
        return customerRepository.findById(query.getId())
                .orElseThrow(()-> new RuntimeException("Customer Not Found"));
    }

}

