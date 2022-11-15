package com.epam.training.ticketservice.presentation.commands;

import com.epam.training.ticketservice.data.movies.MovieService;
import com.epam.training.ticketservice.data.movies.model.MovieDto;
import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;
import com.epam.training.ticketservice.data.rooms.RoomService;
import com.epam.training.ticketservice.data.rooms.model.RoomDto;
import com.epam.training.ticketservice.data.rooms.persistence.entity.Room;
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

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommands {

    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final RoomService roomService;
    private final UserService userService;

    private MovieDto getMovie(String movieName) {
        Optional<Movie> movie = movieService.getSpecificMovie(movieName);
        if (movie.isPresent()) {
            return new MovieDto(movie.get().getName(), movie.get().getGenre(), movie.get().getMovieLength());
        }
        return null;
    }

    private RoomDto getRoom(String roomName) {
        Optional<Room> room = roomService.getSpecificRoom(roomName);
        if (room.isPresent()) {
            return new RoomDto(room.get().getName(), room.get().getChairRowNumber(), room.get().getChairColumnNumber());
        }
        return null;
    }

    private boolean checkIfScreeningTimeBad(Screening screening, MovieDto movieDto) {

        if (screening.getScreeningDate().getTime() < movieDto.getMovieLength() + 10) {
            return true;
        }
        return false;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "create screening <film címe> <terem neve> <vetítés kezdetének dátuma és ideje, YYYY-MM-DD hh:mm formátumban>")
    public String createScreening(String movieName, String roomName, Date screeningDate) {
        RoomDto room = getRoom(roomName);
        MovieDto movie = getMovie(movieName);
        Screening screening = screeningService.getSpecificScreening(movieName, roomName, screeningDate);
        if (screening == null) {
            screeningService.create(movieName, roomName, screeningDate);
            return "screening created";
        } else if (checkIfScreeningTimeBad(screening, movie)) {
            return "This would start in the break period after another screening in this room";
        }
        return "There is an overlapping screening";

    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "delete screening <film címe> <terem neve> <vetítés kezdetének dátuma és ideje, YYYY-MM-DD hh:mm formátumban>")
    public String deleteScreening(String movieName, String roomName, Date screeningDate) {
        RoomDto room = getRoom(roomName);
        MovieDto movie = getMovie(movieName);
        return screeningService.delete(movie, room, screeningDate);
    }

    @ShellMethod(key = "list screenings", value = "returns all screenings")
    public String listAllScreenings() {
        Optional<List<ScreeningDto>> listOfScreenings = screeningService.listAll();

        if (listOfScreenings.isEmpty()) {
            return "There are no screenings";
        }
        StringBuilder toReturn = new StringBuilder();
        //<A film címe> (<műfaj>, <vetítés ideje percben> minutes),
        // screened in room <terem neve>, at <vetítés kezdetének dátuma és ideje, YYYY-MM-DD hh:mm formátumban>
        for (ScreeningDto screening : listOfScreenings.get()) {
            toReturn.append(screening.getMovieName())
                    .append(" (")
                    .append(Objects.requireNonNull(getMovie(screening.getMovieName())).getGenre())
                    .append(", ")
                    .append(Objects.requireNonNull(getMovie(screening.getMovieName())).getMovieLength())
                    .append(" minutes, screened in room ")
                    .append(screening.getRoomName())
                    .append(", at ")
                    .append(screening.getScreeningDate())
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
