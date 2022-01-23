package com.example.customerquerysid.controllers;


import com.example.customerquerysid.entites.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.sid.coreapi.dtos.CustomerRequestDTO;
import org.sid.coreapi.query.GetAllCustomersQuery;
import org.sid.coreapi.query.GetCustomerByIdQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/customers/query")
public class CustomerQueryController {

    private QueryGateway queryGateway;

    @GetMapping("/all")
    public CompletableFuture<List<Customer>> customers(){
        return queryGateway.query(new GetAllCustomersQuery(), ResponseTypes.multipleInstancesOf(Customer.class));
    }

    @GetMapping("/byId/{id}")
    public CompletableFuture<Customer> getCustomer(@PathVariable String id){
        return queryGateway.query(new GetCustomerByIdQuery(id), ResponseTypes.instanceOf(Customer.class));
    }

    @ExceptionHandler
    public ResponseEntity<String> error(Exception ex){
        ResponseEntity<String> responseEntity =new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }



}
