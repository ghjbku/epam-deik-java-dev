package com.epam.training.ticketservice.data.screenings.persistence.repository;

import com.epam.training.ticketservice.data.screenings.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    Optional<Screening> findByMovieMovieNameRoomRoomNameAndScreeningDate(String movieName,
                                                                         String roomName, Date screeningDate);
}
