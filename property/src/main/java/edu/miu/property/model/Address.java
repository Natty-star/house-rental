package edu.miu.property.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
//@AllArgsConstructor
public class Address implements Serializable {
    private String street_number;
    private String city;
    private String state;
    private String zipCode;




    public Address(String city, String state, String zipCode, String street_number) {
        this.street_number = street_number;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;


    }
}
