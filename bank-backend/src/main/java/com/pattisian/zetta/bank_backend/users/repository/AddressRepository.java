package com.pattisian.zetta.bank_backend.users.repository;

import com.pattisian.zetta.bank_backend.users.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
