package com.epam.training.ticketservice.data.screenings.persistence.repository;

import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;
import com.epam.training.ticketservice.data.rooms.persistence.entity.Room;
import com.epam.training.ticketservice.data.screenings.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    Optional<Screening> findByMovieMovieRoomRoomAndScreeningDate(Movie movie, Room room, Date screeningDate);
}
