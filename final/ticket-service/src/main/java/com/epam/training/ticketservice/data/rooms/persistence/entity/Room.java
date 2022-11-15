package com.epam.training.ticketservice.data.rooms.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "Rooms")
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private int chairRowNumber;
    private int chairColumnNumber;

    public Room(String name, int chairRowNumber, int chairColumnNumber) {
        this.name = name;
        this.chairRowNumber = chairRowNumber;
        this.chairColumnNumber = chairColumnNumber;
    }
}
