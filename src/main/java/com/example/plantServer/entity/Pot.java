package com.example.plantServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name="pot")
@NoArgsConstructor
@JsonIgnoreProperties("id")
public class Pot {

    @Id
    @Column(name="serial_id")
    private String serialId;

    @Column(name="humidity")
    private Integer humidity;

    @Column(name="soil_humidity")
    private Integer soil_humidity;

    @Column(name="temper")
    private Integer temper;

    @Column(name="water_level")
    private Integer waterLevel;

    @Column(name="pot_name")
    private String potName;

    @Column(name="plant_name")
    private String plantName;

    @Column(name="water_period")
    private Integer period;

    @Column(name="image_url")
    private String imageUrl;

    @OneToMany( fetch = FetchType.EAGER)
    @JoinColumn(name="serial_id")
    private List<WateringLog> wateringDates = new ArrayList<>();


}
