package cs590.accountservice.entity;

import lombok.Data;

@Data
public class Address {
    private String country;

    private String state;

    private String city;

    private String zipcode;

    private String street;

    public Address(String country, String state, String city, String zipcode, String street) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}

