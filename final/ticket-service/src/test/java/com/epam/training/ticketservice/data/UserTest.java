package com.epam.training.ticketservice.data;

import com.epam.training.ticketservice.data.users.UserService;
import com.epam.training.ticketservice.data.users.UserServiceImpl;
import com.epam.training.ticketservice.data.users.model.UserDto;
import com.epam.training.ticketservice.data.users.persistence.entity.User;
import com.epam.training.ticketservice.data.users.persistence.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTest {


    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService underTest = new UserServiceImpl(userRepository);

    @Test
    public void testUserLoginShouldBeSuccessful() {
        //given
        User testUser = new User("test", "drama", User.Role.ADMIN);

        //when
        when(userRepository.findByUsernameAndPassword(any(), any())).thenReturn(Optional.of(testUser));

        Optional<UserDto> result = underTest.login(testUser.getUsername(), testUser.getPassword());

        //then
        Mockito.verify(userRepository).findByUsernameAndPassword(testUser.getUsername(), testUser.getPassword());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getUsername(), testUser.getUsername());
    }

    @Test
    public void testUserLoginShouldNotBeSuccessful() {
        //given
        User testUser = new User("test", "drama", User.Role.ADMIN);

        //when
        when(userRepository.findByUsernameAndPassword(any(), any())).thenReturn(Optional.empty());

        Optional<UserDto> result = underTest.login(testUser.getUsername(), testUser.getPassword());

        //then
        Mockito.verify(userRepository).findByUsernameAndPassword(testUser.getUsername(), testUser.getPassword());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testUserLogoutShouldBeSuccessful() {
        //given
        User testUser = new User("test", "drama", User.Role.ADMIN);

        //when
        when(userRepository.findByUsernameAndPassword(any(), any())).thenReturn(Optional.of(testUser));

        underTest.login(testUser.getUsername(), testUser.getPassword());

        Optional<UserDto> result = underTest.logout();

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getUsername(), testUser.getUsername());
    }
}
