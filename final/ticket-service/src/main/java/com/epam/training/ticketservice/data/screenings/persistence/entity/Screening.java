package com.epam.training.ticketservice.data.screenings.persistence.entity;

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
    private String movieName;
    private String roomName;
    private Date screeningDate;

}
