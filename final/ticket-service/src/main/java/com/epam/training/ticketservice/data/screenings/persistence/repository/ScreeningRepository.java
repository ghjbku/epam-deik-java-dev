package com.epam.training.ticketservice.data.screenings.persistence.repository;

import com.epam.training.ticketservice.data.screenings.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    @Query(value = "SELECT * FROM Screenings scr WHERE "
            + "EXISTS (SELECT 1 FROM Movies m WHERE m.name = scr.MOVIE_Name AND m.name = ?1) "
            + "AND EXISTS (SELECT 1 FROM Rooms r WHERE r.name = scr.room_Name AND r.name = ?2) "
            + "AND scr.screening_Date = ?3",
            nativeQuery = true)
    Optional<Screening> findByMovieMovieNameRoomRoomNameAndScreeningDate(String movieName,
                                                                         String roomName,
                                                                         Date screeningDate);
}
