package com.epam.training.ticketservice.data.screenings.model;

import lombok.Value;

import java.util.Date;

@Value
public class ScreeningDto {

    private final String movieName;
    private final String roomName;
    //vetítés kezdetének dátuma és ideje, YYYY-MM-DD hh:mm formátumban
    private final Date screeningDate;
}
