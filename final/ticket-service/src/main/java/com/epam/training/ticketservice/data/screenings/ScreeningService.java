package com.epam.training.ticketservice.data.screenings;

import com.epam.training.ticketservice.data.screenings.model.ScreeningDto;
import com.epam.training.ticketservice.data.screenings.persistence.entity.Screening;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    void create(String movieName, String roomName, Date screeningDate);

    String delete(String movieName, String roomName, Date screeningDate);

    Optional<List<ScreeningDto>> listAll();

    Screening getSpecificScreening(String movieName, String roomName);

}
