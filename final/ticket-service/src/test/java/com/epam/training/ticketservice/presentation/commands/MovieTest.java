package com.epam.training.ticketservice.presentation.commands;

import com.epam.training.ticketservice.data.movies.MovieService;
import com.epam.training.ticketservice.data.movies.MovieServiceImpl;
import com.epam.training.ticketservice.data.movies.model.MovieDto;
import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;
import com.epam.training.ticketservice.data.movies.persistence.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieTest {


    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieService underTest = new MovieServiceImpl(movieRepository);

    @Test
    public void testCreateMovieShouldBeSuccessful(){
        //given
        Movie testMovie = new Movie("test","drama",1);

        //when
        when(movieRepository.save(any())).thenReturn(new Movie());

        underTest.create(testMovie.getName(),testMovie.getGenre(),testMovie.getMovieLength());

        //then
        Mockito.verify(movieRepository).save(testMovie);
    }

    @Test
    public void testUpdateMovieShouldBeSuccessful() {
        //given
        Movie testMovie = new Movie("test", "drama", 1);

        //When
        when(movieRepository.save(any())).thenReturn(testMovie);
        when(movieRepository.findByName(any())).thenReturn(Optional.of(testMovie));

        Optional<MovieDto> returnedDto = underTest
                                        .update(testMovie.getName(),testMovie.getGenre(),2);

        //then
        Mockito.verify(movieRepository).save(testMovie);
        Assertions.assertEquals(returnedDto.get().getMovieLength(),2);
    }

    @Test
    public void testUpdateMovieShouldReturnOptionalEmpty(){
        //given
        Movie testMovie = new Movie("test", "drama", 1);

        //when
        when(movieRepository.findByName(any())).thenReturn(Optional.empty());

        Optional<MovieDto> returnedDto = underTest
                .update(testMovie.getName(),testMovie.getGenre(),2);

        //then
        Assertions.assertTrue(returnedDto.isEmpty());
    }

    @Test
    public void testDeleteMovieShouldBeSuccessful(){
        //given
        Movie testMovie = new Movie("test", "drama", 1);

        //when
        when(movieRepository.findByName(any())).thenReturn(Optional.of(testMovie));

        String result = underTest.delete(testMovie.getName());

        //then
        Assertions.assertEquals(result,"deleted the movie called '" + testMovie.getName() + "'");
    }
}
