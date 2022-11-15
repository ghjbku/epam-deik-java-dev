package com.epam.training.ticketservice.data.users.model;

import com.epam.training.ticketservice.data.users.persistence.entity.User;
import lombok.Value;

@Value
public class UserDto {
    private final String username;
    private final User.Role role;
}
