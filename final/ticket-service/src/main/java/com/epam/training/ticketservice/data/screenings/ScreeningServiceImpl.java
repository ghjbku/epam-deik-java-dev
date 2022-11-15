package com.epam.training.ticketservice.data.screenings;

import com.epam.training.ticketservice.data.movies.MovieService;
import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;
import com.epam.training.ticketservice.data.rooms.RoomService;
import com.epam.training.ticketservice.data.rooms.persistence.entity.Room;
import com.epam.training.ticketservice.data.screenings.model.ScreeningDto;
import com.epam.training.ticketservice.data.screenings.persistence.entity.Screening;
import com.epam.training.ticketservice.data.screenings.persistence.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final RoomService roomService;

    @Override
    public void create(String movieName, String roomName, Date screeningDate) {
        Screening newScreening = new Screening(null, movieService.getSpecificMovie(movieName).get(),
                roomService.getSpecificRoom(roomName).get(), screeningDate);
        screeningRepository.save(newScreening);
    }

    @Override
    public String delete(Movie movie, Room room, Date screeningDate) {
        Optional<Screening> roomToDelete = screeningRepository.findByMovieMovieRoomRoomAndScreeningDate(movie, room, screeningDate);
        if (roomToDelete.isEmpty()) {
            return "nothing to delete";
        }

        screeningRepository.delete(roomToDelete.get());

        return "deleted the Screening in room '" + room.getName() + " ' at " + screeningDate + " with the movie " + movie.getName();
    }

    @Override
    public Optional<List<ScreeningDto>> listAll() {
        List<Screening> allScreenings = screeningRepository.findAll();
        if (allScreenings.isEmpty()) {
            return Optional.empty();
        }
        List<ScreeningDto> screeningDtos = new ArrayList<>();
        allScreenings.forEach(screening -> {
            screeningDtos.add(new ScreeningDto(screening.getMovie().getName(),
                    screening.getRoom().getName(), screening.getScreeningDate()));
        });

        return Optional.of(screeningDtos);
    }

    @Override
    public Screening getSpecificScreening(String movieName, String roomName, Date screeningDate) {
        Optional<Screening> toReturn = screeningRepository.
                findByMovieNameRoomNameAndScreeningDate(movieName, roomName, screeningDate);

        return toReturn.orElse(null);
    }
}
