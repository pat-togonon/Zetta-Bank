package com.pattisian.zetta.bank_backend.favorites.controller;

import com.pattisian.zetta.bank_backend.favorites.dto.NewFavoriteRequestDTO;
import com.pattisian.zetta.bank_backend.favorites.dto.UpdateFavoriteRequestDTO;
import com.pattisian.zetta.bank_backend.favorites.entity.Favorite;
import com.pattisian.zetta.bank_backend.favorites.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/banks")
    public List<Favorite> getAllFavoriteBanks() {
        return favoriteService.getAllFavoriteBankAccounts();
    }

    @GetMapping("/billers")
    public List<Favorite> getAllFavoriteBillers() {
        return favoriteService.getAllFavoriteBillers();
    }

    @GetMapping("/{id}")
    public Favorite getFavoriteById(@PathVariable Long id) {
        return favoriteService.getFavoriteById(id);
    }

    @PostMapping("")
    public Favorite addNewFavorite(@RequestBody NewFavoriteRequestDTO request) {
        return favoriteService.addNewFavorite(request);
    }

    @PutMapping("/{id}")
    public Favorite updateFavoriteById(@PathVariable Long id, @RequestBody UpdateFavoriteRequestDTO request) {
        return favoriteService.updateFavorite(request, id);
    }

    @DeleteMapping("/{id}")
    public void deleteFavoriteById(@PathVariable Long id) {
        favoriteService.deleteFavoriteById(id);
    }
}
