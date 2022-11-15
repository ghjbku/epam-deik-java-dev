package com.epam.training.ticketservice.data.rooms;

import com.epam.training.ticketservice.data.rooms.model.RoomDto;
import com.epam.training.ticketservice.data.rooms.persistence.entity.Room;
import com.epam.training.ticketservice.data.rooms.persistence.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public void create(String name, int chairRowNumber, int chairColNumber) {
        Room newRoom = new Room(name, chairRowNumber, chairColNumber);
        roomRepository.save(newRoom);
    }

    @Override
    public Optional<RoomDto> update(String name, int chairRowNumber, int chairColNumber) {
        Optional<Room> roomToUpdate = roomRepository.findByName(name);
        if (roomToUpdate.isEmpty()) {
            return Optional.empty();
        }
        Room updatedRoom = roomToUpdate.get();

        updatedRoom.setName(name);
        updatedRoom.setChairRowNumber(chairRowNumber);
        updatedRoom.setChairColumnNumber(chairColNumber);

        roomRepository.save(updatedRoom);
        return Optional.of(new RoomDto(updatedRoom.getName(), updatedRoom.getChairRowNumber(),
                updatedRoom.getChairColumnNumber()));
    }

    @Override
    public String delete(String name) {
        Optional<Room> roomToDelete = roomRepository.findByName(name);
        if (roomToDelete.isEmpty()) {
            return "nothing to delete";
        }

        roomRepository.delete(roomToDelete.get());

        return "deleted the room called '" + name + "'";
    }

    @Override
    public Optional<List<RoomDto>> listAll() {
        List<Room> allRooms = roomRepository.findAll();
        if (allRooms.isEmpty()) {
            return Optional.empty();
        }
        List<RoomDto> roomDtos = new ArrayList<>();
        allRooms.forEach(room -> {
            roomDtos.add(new RoomDto(room.getName(), room.getChairRowNumber(), room.getChairColumnNumber()));
        });

        return Optional.of(roomDtos);
    }

    @Override
    public Optional<Room> getSpecificRoom(String name) {
        return roomRepository.findByName(name);
    }
}
