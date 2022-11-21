package com.epam.training.ticketservice.presentation.commands;

import com.epam.training.ticketservice.data.movies.MovieService;
import com.epam.training.ticketservice.data.movies.model.MovieDto;
import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;
import com.epam.training.ticketservice.data.screenings.ScreeningService;
import com.epam.training.ticketservice.data.screenings.model.ScreeningDto;
import com.epam.training.ticketservice.data.screenings.persistence.entity.Screening;
import com.epam.training.ticketservice.data.users.UserService;
import com.epam.training.ticketservice.data.users.model.UserDto;
import com.epam.training.ticketservice.data.users.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommands {

    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final UserService userService;

    private final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private MovieDto getMovie(String movieName) {
        Optional<Movie> movie = movieService.getSpecificMovie(movieName);
        return movie.map(value -> new MovieDto(value.getName(), value.getGenre(), value.getMovieLength())).orElse(null);
    }

    private boolean checkIfScreeningTimeOverlaps(List<Screening> fetchedScreening, MovieDto movieDto,
                                                 String roomName, Date newScreeningTime) {
        int newFullScreenTimeInMinutes = 0;
        int fetchedFullScreenTimeInMinutes = 0;
        List<Screening> sameRoom = fetchedScreening.stream().filter(screening -> screening.getRoomName()
                .equals(roomName)).collect(Collectors.toList());

        if (sameRoom.isEmpty()) {
            return false;
        }

        newFullScreenTimeInMinutes = newScreeningTime.getHours() * 60
                + newScreeningTime.getMinutes();

        for (Screening screening : sameRoom) {
            fetchedFullScreenTimeInMinutes = screening.getScreeningDate().getHours() * 60
                    + screening.getScreeningDate().getMinutes();

            if (newFullScreenTimeInMinutes <= movieDto.getMovieLength() + fetchedFullScreenTimeInMinutes
                    & newFullScreenTimeInMinutes >= fetchedFullScreenTimeInMinutes - 10) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfNewScreeningTimeNotOver10Mins(List<Screening> fetchedScreening, MovieDto movieDto,
                                                         String roomName, Date newScreeningTime) {

        long newFullScreenTimeInMinutes = 0;
        long fetchedFullScreenTimeInMinutes = 0;
        List<Screening> sameRoom = fetchedScreening.stream().filter(screening -> screening.getRoomName()
                .equals(roomName)).collect(Collectors.toList());

        if (sameRoom.isEmpty()) {
            return false;
        }

        newFullScreenTimeInMinutes = (newScreeningTime.getTime() / 1000) / 60;

        for (Screening screening : sameRoom) {
            fetchedFullScreenTimeInMinutes = (screening.getScreeningDate().getTime() / 1000) / 60;

            if (newFullScreenTimeInMinutes <= (getMovie(screening.getMovieName()).getMovieLength()
                    + fetchedFullScreenTimeInMinutes + 10)
                    && newFullScreenTimeInMinutes >= fetchedFullScreenTimeInMinutes - 10) {
                return true;
            }
        }
        return false;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening",
            value = "create screening <film címe> <terem neve> <vetítés kezdete YYYY-MM-DD hh:mm formátumban>")
    public String createScreening(String movieName, String roomName, String screeningDate) {
        MovieDto movie = getMovie(movieName);

        Date formattedDate = new Date();
        try {
            formattedDate = sf.parse(screeningDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        List<Screening> screening = screeningService.getSpecificScreeningByRoom(roomName);

        if (screening == null) {
            screeningService.create(movieName, roomName, formattedDate);
            return "screening created";
        } else if (checkIfScreeningTimeOverlaps(screening, movie, roomName, formattedDate)) {
            return "There is an overlapping screening";
        } else if (checkIfNewScreeningTimeNotOver10Mins(screening, movie, roomName, formattedDate)) {
            return "This would start in the break period after another screening in this room";
        }
        screeningService.create(movieName, roomName, formattedDate);
        return "screening created";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening",
            value = "delete screening <film címe> <terem neve> <vetítés kezdete YYYY-MM-DD hh:mm formátumban>")
    public String deleteScreening(String movieName, String roomName, String screeningDate) {
        Date formattedDate = new Date();
        try {
            formattedDate = sf.parse(screeningDate);
        } catch (java.text.ParseException exc) {
            exc.printStackTrace();
        }
        return screeningService.delete(movieName, roomName, formattedDate);

    }

    @ShellMethod(key = "list screenings", value = "returns all screenings")
    public String listAllScreenings() {
        Optional<List<ScreeningDto>> listOfScreenings = screeningService.listAll();

        if (listOfScreenings.isEmpty()) {
            return "There are no screenings";
        }
        StringBuilder toReturn = new StringBuilder();

        for (ScreeningDto screening : listOfScreenings.get()) {
            toReturn.append(screening.getMovieName())
                    .append(" (")
                    .append(Objects.requireNonNull(getMovie(screening.getMovieName())).getGenre())
                    .append(", ")
                    .append(Objects.requireNonNull(getMovie(screening.getMovieName())).getMovieLength())
                    .append(" minutes), screened in room ")
                    .append(screening.getRoomName())
                    .append(", at ")
                    .append(sf.format(screening.getScreeningDate()))
                    .append("\n");

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
