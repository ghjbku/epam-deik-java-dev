package com.epam.training.ticketservice.data.screenings.persistence.entity;

import com.epam.training.ticketservice.data.movies.persistence.entity.Movie;
import com.epam.training.ticketservice.data.rooms.persistence.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Screenings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private Movie movie;
    @OneToOne
    private Room room;
    private Date screeningDate;

}
