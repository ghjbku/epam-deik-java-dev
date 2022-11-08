package com.epam.training.ticketservice.data.movies.model;

import lombok.Value;

@Value
public class MovieDto {

    private final String movieName;
    private final String genre;
    private final int movieLength;
}
