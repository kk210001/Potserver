package com.example.plantServer.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "watering_log")
@NoArgsConstructor
public class WateringLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //@Column(name ="watering_date")
    //private Date date;


}
