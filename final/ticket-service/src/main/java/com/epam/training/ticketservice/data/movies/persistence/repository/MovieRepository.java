package com.epam.training.ticketservice.data.movies.persistence.repository;

import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findByName(String name);

}
