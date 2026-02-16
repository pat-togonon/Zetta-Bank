package com.pattisian.zetta.bank_backend.favorites.repository;

import com.pattisian.zetta.bank_backend.favorites.dto.NewFavoriteRequestDTO;
import com.pattisian.zetta.bank_backend.favorites.dto.UpdateFavoriteRequestDTO;
import com.pattisian.zetta.bank_backend.favorites.entity.Favorite;
import com.pattisian.zetta.bank_backend.favorites.enums.FavoriteType;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("SELECT f FROM Favorite f WHERE f.user = :user AND f.type = com.pattisian.zetta.bank_backend.favorites.enums.FavoriteType.BILLER")
    List<Favorite> getAllFavoriteBillers(@Param("user") User user);

    @Query("SELECT f FROM Favorite f WHERE f.user = :user AND f.type = com.pattisian.zetta.bank_backend.favorites.enums.FavoriteType.BANK")
    List<Favorite> getAllFavoriteBankAccounts(@Param("user") User user);

    @Query("SELECT f FROM Favorite f WHERE f.user = :user AND f.id = :id")
    Optional<Favorite> getFavoriteById(@Param("user") User user, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.user = :user and f.id = :id")
    void deleteFavoriteById(@Param("user") User user, @Param("id") Long id);

}


