package com.epam.training.ticketservice.data.screenings;

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

    @Override
    public void create(String movieName, String roomName, Date screeningDate) {
        Screening newScreening = new Screening(null, movieName, roomName, screeningDate);
        screeningRepository.save(newScreening);
    }

    @Override
    public void delete(String movieName, String roomName, Date screeningDate) {
        Optional<Screening> roomToDelete = screeningRepository
                .findByMovieNameRoomNameAndScreeningDate(movieName, roomName, screeningDate);

        roomToDelete.ifPresent(screeningRepository::delete);

    }

    @Override
    public Optional<List<ScreeningDto>> listAll() {
        List<Screening> allScreenings = screeningRepository.findAll();
        if (allScreenings.isEmpty()) {
            return Optional.empty();
        }
        List<ScreeningDto> screeningDtos = new ArrayList<>();
        allScreenings.forEach(screening -> {
            screeningDtos.add(new ScreeningDto(screening.getMovieName(),
                    screening.getRoomName(), screening.getScreeningDate()));
        });

        return Optional.of(screeningDtos);
    }

    @Override
    public Screening getSpecificScreening(String movieName, String roomName) {
        Optional<Screening> toReturn = screeningRepository
                .findByMovienameAndRoomname(movieName, roomName);

        return toReturn.orElse(null);
    }

    @Override
    public List<Screening> getSpecificScreeningByRoom(String roomName) {
        Optional<List<Screening>> toReturn = screeningRepository
                .findByRoomName(roomName);

        return toReturn.orElse(null);
    }
}
