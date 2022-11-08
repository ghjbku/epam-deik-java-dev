package com.epam.training.ticketservice.data.user;

import com.epam.training.ticketservice.data.user.model.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> login(String username, String password);

    Optional<UserDto> logout();

    Optional<UserDto> describe();

    void registerUser(String username, String password);
}
