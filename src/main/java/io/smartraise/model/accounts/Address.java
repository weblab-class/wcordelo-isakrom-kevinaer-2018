package io.smartraise.model.accounts;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Address {

    @Id
    private String id;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    public Address() {
        this.id = UUID.randomUUID().toString();
        this.country = "USA";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
