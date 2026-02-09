package com.pattisian.zetta.bank_backend.users.entity;

import com.pattisian.zetta.bank_backend.users.enums.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "house_number", nullable = false)
    @Size(min = 1, max = 10)
    @NotBlank
    private String houseNumber;

    @Column(nullable = false)
    @Size(min = 5, max = 50)
    @NotBlank
    private String street;

    @Column(nullable = false)
    @Size(min = 3, max = 50)
    @NotBlank
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)// for the word Philippines
    @NotNull
    private Country country;

    @Column(name = "zip_code", nullable = false, length = 4)
    private int zipCode;

    public Address() {
    }

    public Address(User user, String houseNumber, String street, String city, int zipCode) {
        this.user = user;
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.country = Country.PHILIPPINES;
        this.zipCode = zipCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", user=" + user +
                ", houseNumber='" + houseNumber + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
