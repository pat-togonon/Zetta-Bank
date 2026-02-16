package com.pattisian.zetta.bank_backend.security.context;

import com.pattisian.zetta.bank_backend.common.helpers.SecurityUtil;
import com.pattisian.zetta.bank_backend.users.entity.User;
import com.pattisian.zetta.bank_backend.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {

    private final UserRepository userRepository;

    public AuthenticatedUserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthenticatedUser() {
        String username = SecurityUtil.getLoggedInUsername();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Invalid request."));
    }

}
