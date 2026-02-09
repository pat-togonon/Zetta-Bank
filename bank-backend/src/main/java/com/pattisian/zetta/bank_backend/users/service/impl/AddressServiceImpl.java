package com.pattisian.zetta.bank_backend.users.service.impl;

import com.pattisian.zetta.bank_backend.users.entity.Address;
import com.pattisian.zetta.bank_backend.users.repository.AddressRepository;
import com.pattisian.zetta.bank_backend.users.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }
}
