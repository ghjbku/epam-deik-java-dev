package com.epam.training.ticketservice.presentation.commands;

import com.epam.training.ticketservice.data.rooms.RoomService;
import com.epam.training.ticketservice.data.rooms.model.RoomDto;
import com.epam.training.ticketservice.data.users.UserService;
import com.epam.training.ticketservice.data.users.model.UserDto;
import com.epam.training.ticketservice.data.users.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class RoomCommands {

    private final RoomService roomService;
    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "create room <terem neve> <széksorok száma> <szék oszlopok száma>")
    public void createRoom(String name, int rowNumber, int colNumber) {
        roomService.create(name, rowNumber, colNumber);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "update room <terem neve> <széksorok száma> <szék oszlopok száma>")
    public String updateRoom(String name, int rowNumber, int colNumber) {
        Optional<RoomDto> updated = roomService.update(name, rowNumber, colNumber);

        if (updated.isEmpty()) {
            return "Nothing was updated";
        }
        return "room with name '" + updated.get().getRoomName() + "' was updated";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "delete room <terem neve>")
    public String deleteRoom(String name) {
        return roomService.delete(name);
    }

    @ShellMethod(key = "list rooms", value = "returns all rooms")
    public String listAllRooms() {
        Optional<List<RoomDto>> listOfRooms = roomService.listAll();
        if (listOfRooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        StringBuilder toReturn = new StringBuilder();
        //Room <terem neve> with <székek száma> seats, <széksorok száma> rows and <szék oszlopok száma> columns
        for (RoomDto room : listOfRooms.get()) {
            toReturn.append("Room ")
                    .append(room.getRoomName())
                    .append(" with ")
                    .append(room.getChairColumnNumber()*room.getChairRowNumber())
                    .append(" seats, ")
                    .append(room.getChairRowNumber())
                    .append(" rows and ")
                    .append(room.getChairColumnNumber()).append(" columns\n");
        }

        return toReturn.deleteCharAt(toReturn.length() - 1).toString();
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().getRole() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
