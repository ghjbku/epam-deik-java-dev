package com.epam.training.ticketservice.data.configuration;


import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;
import com.epam.training.ticketservice.data.movies.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.data.rooms.persistence.entity.Room;
import com.epam.training.ticketservice.data.rooms.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.data.users.persistence.entity.User;
import com.epam.training.ticketservice.data.users.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);

        movieRepository.save(new Movie("rambo","drama",20));
        roomRepository.save(new Room("asd",1,2));
    }
}
