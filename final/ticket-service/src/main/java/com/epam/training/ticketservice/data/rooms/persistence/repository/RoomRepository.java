package com.epam.training.ticketservice.data.rooms.persistence.repository;

import com.epam.training.ticketservice.data.rooms.persistence.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByName(String name);
}
