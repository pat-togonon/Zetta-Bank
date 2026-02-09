package com.pattisian.zetta.bank_backend.users.repository;

import com.pattisian.zetta.bank_backend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
