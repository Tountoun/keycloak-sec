package com.gofar.controller;

import com.gofar.dto.CustomerDto;
import com.gofar.entity.Customer;
import com.gofar.exception.CustomerException;
import com.gofar.service.CustomerService;
import com.gofar.utlis.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Response> save(@RequestBody CustomerDto customerDto) {
        Response response;
        try {
            Customer customer = this.customerService.create(customerDto);
            response = new Response(HttpStatus.OK.name(), customer, "Success");
        } catch (CustomerException e) {
            response = new Response(HttpStatus.NOT_FOUND.name(), null, "Failure: %s".formatted(e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

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

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        Response response;
        try {
            boolean deleted = this.customerService.deleteById(id);
            response = new Response(HttpStatus.OK.name(), true, "Success: Customer deleted");
        } catch (CustomerException e) {
            response = new Response(HttpStatus.NOT_FOUND.name(), false, "Failure: Customer does not exist");
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}/disable")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> disable(@PathVariable Long id) {
        Response response;
        try {
            this.customerService.disable(id);
            response  = new Response(HttpStatus.OK.name(), null, "Success");
        } catch (CustomerException e) {
            response = new Response(HttpStatus.NOT_FOUND.name(), null, "Failure: %s".formatted(e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}/enable")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> enable(@PathVariable Long id) {
        Response response;
        try {
            this.customerService.enable(id);
            response  = new Response(HttpStatus.OK.name(), null, "Success");
        } catch (CustomerException e) {
            response = new Response(HttpStatus.NOT_FOUND.name(), null, "Failure: %s".formatted(e.getMessage()));
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
