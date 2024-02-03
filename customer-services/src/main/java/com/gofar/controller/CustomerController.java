package com.gofar.controller;

import com.gofar.entity.Customer;
import com.gofar.exception.CustomerException;
import com.gofar.service.CustomerService;
import com.gofar.utlis.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping(value = "id/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        Response response;
        try {
            Customer customer = this.customerService.getById(id);
            response  = new Response(HttpStatus.OK.name(), customer, "Success");
        } catch (CustomerException e) {
            response = new Response(HttpStatus.NOT_FOUND.name(), null, "Failure: Customer not found");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "email/{email}")
    public ResponseEntity<Response> getByEmail(@PathVariable String email) {
        Response response;
        try {
            Customer customer = this.customerService.getByEmail(email);
            response  = new Response(HttpStatus.OK.name(), customer, "Success");
        } catch (CustomerException e) {
            response = new Response(HttpStatus.NOT_FOUND.name(), null, "Failure: Customer not found");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(new Response(
                "200", this.customerService.getAll(), "Customers retrieved successfully"
        ));
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
