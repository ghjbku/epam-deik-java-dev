package com.epam.training.ticketservice.data.rooms.model;

import lombok.Value;

@Value
public class RoomDto {

    private final String roomName;
    private final int chairRowNumber;
    private final int chairColumnNumber;
}
