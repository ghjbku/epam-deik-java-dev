package com.epam.training.ticketservice.data.movies;

import com.epam.training.ticketservice.data.movies.model.MovieDto;
import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;
import com.epam.training.ticketservice.data.movies.persistence.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public void create(String name, String genre, int movieLength) {
        Movie newMovie = new Movie(name, genre, movieLength);
        movieRepository.save(newMovie);
    }

    @Override
    public Optional<MovieDto> update(String name, String genre, int movieLength) {
        Optional<Movie> movieToUpdate = movieRepository.findByName(name);
        if (movieToUpdate.isEmpty()) {
            return Optional.empty();
        }
        Movie updatedMovie = movieToUpdate.get();

        updatedMovie.setName(name);
        updatedMovie.setGenre(genre);
        updatedMovie.setMovieLength(movieLength);

        movieRepository.save(updatedMovie);
        return Optional.of(new MovieDto(updatedMovie.getName(), updatedMovie.getGenre(),
                updatedMovie.getMovieLength()));
    }

    @Override
    public String delete(String name) {
        Optional<Movie> movieToDelete = movieRepository.findByName(name);
        if (movieToDelete.isEmpty()) {
            return "nothing to delete";
        }

        movieRepository.delete(movieToDelete.get());

        return "deleted the movie called '" + name + "'";
    }

    @Override
    public Optional<List<MovieDto>> listAll() {
        List<Movie> allMovies = movieRepository.findAll();
        if (allMovies.isEmpty()) {
            return Optional.empty();
        }
        List<MovieDto> movieDtos = new ArrayList<>();
        allMovies.forEach(movie -> {
            movieDtos.add(new MovieDto(movie.getName(), movie.getGenre(), movie.getMovieLength()));
        });

        return Optional.of(movieDtos);
    }

    public Optional<Movie> getSpecificMovie(String name) {
        return movieRepository.findByName(name);
    }
}
