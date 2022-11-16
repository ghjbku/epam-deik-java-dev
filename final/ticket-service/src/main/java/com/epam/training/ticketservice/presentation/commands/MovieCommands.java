package com.epam.training.ticketservice.presentation.commands;

import com.epam.training.ticketservice.data.movies.MovieService;
import com.epam.training.ticketservice.data.movies.model.MovieDto;
import com.epam.training.ticketservice.data.users.UserService;
import com.epam.training.ticketservice.data.users.model.UserDto;
import com.epam.training.ticketservice.data.users.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class MovieCommands {

    private final MovieService movieService;
    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "create movie <film címe> <műfaj> <vetítés hossza percben>")
    public String createMovie(String name, String genre, int len) {
        movieService.create(name, genre, len);
        return "movie with name '" + name + "' was created";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "update movie <film címe> <műfaj> <vetítés hossza percben>")
    public String updateMovie(String name, String genre, int len) {
        Optional<MovieDto> updated = movieService.update(name, genre, len);

        if (updated.isEmpty()) {
            return "Nothing was updated";
        }
        return "movie with name '" + updated.get().getMovieName() + "' was updated";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "delete movie <film címe>")
    public String deleteMovie(String name) {
        return movieService.delete(name);
    }

    @ShellMethod(key = "list movies", value = "returns all movies")
    public String listAllMovies() {
        Optional<List<MovieDto>> listOfMovies = movieService.listAll();
        if (listOfMovies.isEmpty()) {
            return "There are no movies at the moment";
        }
        StringBuilder toReturn = new StringBuilder();
        for (MovieDto movie : listOfMovies.get()) {
            toReturn.append(movie.getMovieName())
                    .append(" (")
                    .append(movie.getGenre())
                    .append(", ")
                    .append(movie.getMovieLength()).append(" minutes)\n");
        }

        return toReturn.deleteCharAt(toReturn.length() - 1).toString();
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().getRole() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
