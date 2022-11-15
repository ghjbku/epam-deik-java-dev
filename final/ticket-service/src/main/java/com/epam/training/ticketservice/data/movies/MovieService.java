package com.epam.training.ticketservice.data.movies;

import com.epam.training.ticketservice.data.movies.model.MovieDto;
import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    void create(String name,String genre,int movieLength);

    Optional<MovieDto> update(String name,String genre,int movieLength);

    String delete(String name);

    Optional<List<MovieDto>> listAll();

    Optional<Movie> getSpecificMovie(String name);

}
