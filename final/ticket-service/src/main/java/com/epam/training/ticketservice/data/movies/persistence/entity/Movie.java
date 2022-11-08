package com.epam.training.ticketservice.data.movies.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;


@Entity
@Table(name = "Movies")
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private String genre;
    private int movieLength;

    public Movie(String name, String genre, int movieLength) {
        this.name = name;
        this.genre = genre;
        this.movieLength = movieLength;
    }
}
