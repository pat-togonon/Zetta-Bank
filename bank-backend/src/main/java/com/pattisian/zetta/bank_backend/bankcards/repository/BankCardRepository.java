package com.pattisian.zetta.bank_backend.bankcards.repository;

import com.pattisian.zetta.bank_backend.bankcards.entity.BankCard;
import com.pattisian.zetta.bank_backend.bankcards.enums.Status;
import com.pattisian.zetta.bank_backend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, Long> {

    @Query("SELECT b FROM BankCard b WHERE b.user = :user AND b.id = :id AND b.status = com.pattisian.zetta.bank_backend.bankcards.enums.Status.ACTIVE")
    Optional<BankCard> getBankCardById(@Param("user") User user, @Param("id") Long id);

    @Query("SELECT b FROM BankCard b WHERE b.user = :user AND b.status = com.pattisian.zetta.bank_backend.bankcards.enums.Status.ACTIVE")
    Optional<BankCard> getBankCardByUserToDeactivate(@Param("user") User user);

    @Query("SELECT b FROM BankCard b WHERE b.user = :user AND b.id = :id")
    Optional<BankCard> getBankCardToUpdateById(@Param("user") User user, @Param("id") Long id);

    @Query("SELECT b FROM BankCard b WHERE b.user = :user AND b.status = :status" )
    List<BankCard> getAllBankCardsByUser(@Param("user") User user, @Param("status") Status status);
}
