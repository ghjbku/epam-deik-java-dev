package com.epam.training.ticketservice.data.rooms;

import com.epam.training.ticketservice.data.rooms.model.RoomDto;
import com.epam.training.ticketservice.data.rooms.persistence.entity.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    void create(String name,int chairRowNumber,int chairColNumber);

    Optional<RoomDto> update(String name,int chairRowNumber,int chairColNumber);

    String delete(String name);

    Optional<List<RoomDto>> listAll();

    Optional<Room> getSpecificRoom(String name);

}
