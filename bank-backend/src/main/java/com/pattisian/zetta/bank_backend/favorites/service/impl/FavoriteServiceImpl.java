package com.pattisian.zetta.bank_backend.favorites.service.impl;

import com.pattisian.zetta.bank_backend.bankList.entity.Bank;
import com.pattisian.zetta.bank_backend.bankList.repository.BankListRepository;
import com.pattisian.zetta.bank_backend.billers.entity.Biller;
import com.pattisian.zetta.bank_backend.billers.repository.BillerRepository;
import com.pattisian.zetta.bank_backend.common.exception.InvalidBankListException;
import com.pattisian.zetta.bank_backend.common.exception.InvalidBillerException;
import com.pattisian.zetta.bank_backend.common.exception.InvalidFavoriteTypeException;
import com.pattisian.zetta.bank_backend.favorites.dto.NewFavoriteRequestDTO;
import com.pattisian.zetta.bank_backend.favorites.dto.UpdateFavoriteRequestDTO;
import com.pattisian.zetta.bank_backend.favorites.entity.Favorite;
import com.pattisian.zetta.bank_backend.favorites.enums.FavoriteType;
import com.pattisian.zetta.bank_backend.favorites.repository.FavoriteRepository;
import com.pattisian.zetta.bank_backend.favorites.service.FavoriteService;
import com.pattisian.zetta.bank_backend.security.context.AuthenticatedUserProvider;
import com.pattisian.zetta.bank_backend.users.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final BankListRepository bankListRepository;
    private final BillerRepository billerRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, AuthenticatedUserProvider authenticatedUserProvider, BankListRepository bankListRepository, BillerRepository billerRepository) {
        this.favoriteRepository = favoriteRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.bankListRepository = bankListRepository;
        this.billerRepository = billerRepository;
    }

    @Override
    public List<Favorite> getAllFavoriteBillers() {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        return favoriteRepository.getAllFavoriteBillers(user);
    }

    @Override
    public List<Favorite> getAllFavoriteBankAccounts() {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        return favoriteRepository.getAllFavoriteBankAccounts(user);
    }

    @Override
    public Favorite getFavoriteById(Long id) {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        return favoriteRepository.getFavoriteById(user, id)
                .orElseThrow(() -> new EntityNotFoundException("Favorite is not found."));
    }

    @Transactional
    @Override
    public Favorite addNewFavorite(NewFavoriteRequestDTO request) {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        Favorite newFavorite = null;
        if (request.getType() == FavoriteType.BANK) {
            Bank bank = bankListRepository.findById(request.getBankId())
                    .orElseThrow(() -> new InvalidBankListException("Bank does not exist."));
            newFavorite = Favorite.builder(request.getType(), user, request.getAccountName(), request.getAccountNumber(), request.getNickname())
                    .setBankId(bank.getId())
                    .build();
        } else if (request.getType() == FavoriteType.BILLER) {
            Biller biller = billerRepository.findById(request.getBillerId())
                    .orElseThrow(() -> new InvalidBillerException("Biller does not exist."));
            newFavorite = Favorite.builder(request.getType(), user, request.getAccountName(), request.getAccountNumber(), request.getNickname())
                    .setBillerId(biller.getId())
                    .build();
        } else {
            throw new InvalidFavoriteTypeException("You can only save billers or banks in your favorites.");
        }

        return favoriteRepository.save(newFavorite);
    }

    @Transactional
    @Override
    public Favorite updateFavorite(UpdateFavoriteRequestDTO request, Long id) {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        Favorite favoriteToUpdate = favoriteRepository.getFavoriteById(user, id)
                .orElseThrow(() -> new EntityNotFoundException("Favorite not found."));

        if (request.getAccountName() != null) {
            favoriteToUpdate.setAccountName(request.getAccountName());
        }

        if (request.getAccountNumber() != null) {
            favoriteToUpdate.setAccountNumber(request.getAccountNumber());
        }

        if (request.getNickname() !=  null) {
            favoriteToUpdate.setNickname(request.getNickname());
        }

        return favoriteRepository.save(favoriteToUpdate);
    }

    @Transactional
    @Override
    public void deleteFavoriteById(Long id) {
        User user = authenticatedUserProvider.getAuthenticatedUser();
        favoriteRepository.deleteFavoriteById(user, id);
    }
}
