package com.epam.training.ticketservice.data;

import com.epam.training.ticketservice.data.movies.MovieService;
import com.epam.training.ticketservice.data.movies.MovieServiceImpl;
import com.epam.training.ticketservice.data.movies.model.MovieDto;
import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;
import com.epam.training.ticketservice.data.movies.persistence.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieTest {


    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieService underTest = new MovieServiceImpl(movieRepository);

    @Test
    public void testCreateMovieShouldBeSuccessful() {
        //given
        Movie testMovie = new Movie("test", "drama", 1);

        //when
        when(movieRepository.save(any())).thenReturn(new Movie());

        underTest.create(testMovie.getName(), testMovie.getGenre(), testMovie.getMovieLength());

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
                .update(testMovie.getName(), testMovie.getGenre(), 2);

        //then
        Mockito.verify(movieRepository).save(testMovie);
        Assertions.assertTrue(returnedDto.isPresent());
        Assertions.assertEquals(returnedDto.get().getMovieLength(), 2);
    }

    @Test
    public void testUpdateMovieShouldReturnOptionalEmpty() {
        //given
        Movie testMovie = new Movie("test", "drama", 1);

        //when
        when(movieRepository.findByName(any())).thenReturn(Optional.empty());

        Optional<MovieDto> returnedDto = underTest
                .update(testMovie.getName(), testMovie.getGenre(), 2);

        //then
        Assertions.assertTrue(returnedDto.isEmpty());
    }

    @Test
    public void testDeleteMovieShouldBeSuccessful() {
        //given
        Movie testMovie = new Movie("test", "drama", 1);

        //when
        when(movieRepository.findByName(any())).thenReturn(Optional.of(testMovie));

        String result = underTest.delete(testMovie.getName());

        //then
        Assertions.assertEquals(result, "deleted the movie called '" + testMovie.getName() + "'");
    }

    @Test
    public void testDeleteMovieShouldReturnEmpty() {
        //given
        Movie testMovie = new Movie("test", "drama", 1);

        //when
        when(movieRepository.findByName(any())).thenReturn(Optional.empty());

        String result = underTest.delete(testMovie.getName());

        //then
        Mockito.verify(movieRepository).findByName(testMovie.getName());
        Assertions.assertEquals(result, "nothing to delete");
    }

    @Test
    public void testListAllMovieShouldReturnNonEmpty() {
        //given
        Movie testMovie = new Movie("test", "drama", 1);
        MovieDto testDto = new MovieDto(testMovie.getName(), testMovie.getGenre(), testMovie.getMovieLength());

        //when
        when(movieRepository.findAll()).thenReturn(List.of(testMovie));

        Optional<List<MovieDto>> result = underTest.listAll();

        //then
        Mockito.verify(movieRepository).findAll();
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().get(0).getMovieName(), testDto.getMovieName());
        Assertions.assertEquals(result.get().get(0).getGenre(), testDto.getGenre());
        Assertions.assertEquals(result.get().get(0).getMovieLength(), testDto.getMovieLength());

    }

    @Test
    public void testListAllMovieShouldReturnEmpty() {
        //given

        //when
        when(movieRepository.findAll()).thenReturn(List.of());

        Optional<List<MovieDto>> result = underTest.listAll();

        //then
        Mockito.verify(movieRepository).findAll();
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetSpecificMovieShouldReturnMovie() {
        //given
        Movie testMovie = new Movie("test", "drama", 1);

        //when
        when(movieRepository.findByName(any())).thenReturn(Optional.of(testMovie));

        Optional<Movie> result = underTest.getSpecificMovie(testMovie.getName());

        //then
        Mockito.verify(movieRepository).findByName(testMovie.getName());
        Assertions.assertTrue(result.isPresent());
    }
}
