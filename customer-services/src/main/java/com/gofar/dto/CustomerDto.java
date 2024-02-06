package com.gofar.dto;


import com.gofar.entity.Address;
import com.gofar.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String name;
    private String email;
    private String phone;
    private String city;
    private String road;
    private int postal_code;

    public Customer getCustomer() {
        Customer customer = new Customer();

        Address address = new Address();
        address.setCity(city);
        address.setRoad(road);
        address.setPostal_code(postal_code);

        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);

        return customer;
    }
}
