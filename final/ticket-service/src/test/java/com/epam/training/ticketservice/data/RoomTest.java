package com.epam.training.ticketservice.data;

import com.epam.training.ticketservice.data.rooms.RoomService;
import com.epam.training.ticketservice.data.rooms.RoomServiceImpl;
import com.epam.training.ticketservice.data.rooms.model.RoomDto;
import com.epam.training.ticketservice.data.rooms.persistence.entity.Room;
import com.epam.training.ticketservice.data.rooms.persistence.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RoomTest {


    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomService underTest = new RoomServiceImpl(roomRepository);

    @Test
    public void testCreateRoomShouldBeSuccessful() {
        //given
        Room testRoom = new Room("test", 1, 1);

        //when
        when(roomRepository.save(any())).thenReturn(new Room());

        underTest.create(testRoom.getName(), testRoom.getChairRowNumber(), testRoom.getChairColumnNumber());

        //then
        Mockito.verify(roomRepository).save(testRoom);
    }

    @Test
    public void testUpdateRoomShouldBeSuccessful() {
        //given
        Room testRoom = new Room("test", 1, 1);

        //when
        when(roomRepository.findByName(any())).thenReturn(java.util.Optional.of(testRoom));

        Optional<RoomDto> result = underTest.update(testRoom.getName(), 2, 2);

        //then
        Mockito.verify(roomRepository).findByName(testRoom.getName());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getRoomName(), testRoom.getName());
        Assertions.assertEquals(result.get().getChairColumnNumber(), 2);
        Assertions.assertEquals(result.get().getChairRowNumber(), 2);
        Assertions.assertEquals(result.get(),
                new RoomDto(testRoom.getName(), 2, 2));
    }

    @Test
    public void testUpdateRoomShouldReturnEmpty() {
        //given
        Room testRoom = new Room("test", 1, 1);

        //when
        when(roomRepository.findByName(any())).thenReturn(java.util.Optional.empty());

        Optional<RoomDto> result = underTest.update(testRoom.getName(), 2, 2);

        //then
        Mockito.verify(roomRepository).findByName(testRoom.getName());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testDeleteRoomShouldBeSuccessful() {
        //given
        Room testRoom = new Room("test", 1, 1);

        //when
        when(roomRepository.findByName(any())).thenReturn(java.util.Optional.of(testRoom));
        doNothing().when(roomRepository).delete(any());

        String result = underTest.delete(testRoom.getName());

        //then
        Mockito.verify(roomRepository).findByName(testRoom.getName());
        Mockito.verify(roomRepository).delete(testRoom);
        Assertions.assertEquals(result, "deleted the room called '" + testRoom.getName() + "'");
    }

    @Test
    public void testDeleteRoomShouldNotBeSuccessful() {
        //given
        Room testRoom = new Room("test", 1, 1);

        //when
        when(roomRepository.findByName(any())).thenReturn(java.util.Optional.empty());

        String result = underTest.delete(testRoom.getName());

        //then
        Mockito.verify(roomRepository).findByName(testRoom.getName());
        Assertions.assertEquals(result, "nothing to delete");
    }

    @Test
    public void testListAllRoomShouldBeSuccessful() {
        //given
        Room testRoom = new Room("test", 1, 1);
        Room testRoom2 = new Room("test2", 1, 1);

        //when
        when(roomRepository.findAll()).thenReturn(List.of(testRoom, testRoom2));

        Optional<List<RoomDto>> resultList = underTest.listAll();

        //then
        Mockito.verify(roomRepository).findAll();
        Assertions.assertTrue(resultList.isPresent());
        Assertions.assertEquals(resultList.get().get(0),
                new RoomDto(testRoom.getName(), 1, 1));
    }

    @Test
    public void testListAllRoomShouldNotBeSuccessful() {
        //given

        //when
        when(roomRepository.findAll()).thenReturn(List.of());

        Optional<List<RoomDto>> resultList = underTest.listAll();

        //then
        Mockito.verify(roomRepository).findAll();
        Assertions.assertTrue(resultList.isEmpty());
    }

    @Test
    public void testGetSpecificRoomRoomShouldBeSuccessful() {
        //given
        Room testRoom = new Room("test", 1, 1);

        //when
        when(roomRepository.findByName(any())).thenReturn(Optional.of(testRoom));

        Optional<Room> result = underTest.getSpecificRoom(testRoom.getName());

        //then
        Mockito.verify(roomRepository).findByName(testRoom.getName());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getName(), testRoom.getName());
    }


}
