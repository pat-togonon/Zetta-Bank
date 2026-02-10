package com.pattisian.zetta.bank_backend.users.service;

import com.pattisian.zetta.bank_backend.users.entity.User;

public interface UserService {

    public User createNewUser(User user);

    public User getUserById(Long userId);

    public User getUserByUsername(String username);
}
