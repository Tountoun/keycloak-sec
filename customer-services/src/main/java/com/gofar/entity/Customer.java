package com.gofar.entity;

import com.gofar.exception.CustomerException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String phone;
    @Embedded
    @AttributeOverride(name = "postal_code", column = @Column(name = "pcode"))
    private Address address;

    @Enumerated(EnumType.STRING)
    private State state = State.ENABLED;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void disable() throws CustomerException {
        if (this.state == State.DELETED) {
            throw new CustomerException("Customer deleted. This operation is not allowed");
        }
        if (this.state == State.DISABLED) {
            throw new CustomerException("Customer already disabled.");
        }
        this.setState(state);
    }

    public void enable() throws CustomerException {
        if (this.state == State.DELETED) {
            throw new CustomerException("Customer deleted. This operation is not allowed");
        }
        if (this.state == State.ENABLED) {
            throw new CustomerException("Customer already enabled.");
        }
        this.setState(state);
    }
}
