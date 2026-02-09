package com.pattisian.zetta.bank_backend.users.dto;

public class UserAddressDetails {
    private String houseNumber;
    private String street;
    private String city;
    private int zipCode;


    public UserAddressDetails(String houseNumber, String street, String city, int zipCode) {
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "UserAddressDetails{" +
                ", houseNumber='" + houseNumber + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
