package com.epam.training.ticketservice.data.movies;

import com.epam.training.ticketservice.data.movies.model.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    void create(String name,String genre,int movieLength);

    Optional<MovieDto> update(String name,String genre,int movieLength);

    String delete(String name);

    Optional<List<MovieDto>> listAll();

}
