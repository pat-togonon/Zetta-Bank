package com.pattisian.zetta.bank_backend.favorites.service;

import com.pattisian.zetta.bank_backend.favorites.dto.NewFavoriteRequestDTO;
import com.pattisian.zetta.bank_backend.favorites.dto.UpdateFavoriteRequestDTO;
import com.pattisian.zetta.bank_backend.favorites.entity.Favorite;

import java.util.List;

public interface FavoriteService {

    List<Favorite> getAllFavoriteBillers();

    List<Favorite> getAllFavoriteBankAccounts();

    Favorite getFavoriteById(Long id);

    Favorite addNewFavorite(NewFavoriteRequestDTO request);

    Favorite updateFavorite(UpdateFavoriteRequestDTO request, Long id);

    void deleteFavoriteById(Long id);

}
