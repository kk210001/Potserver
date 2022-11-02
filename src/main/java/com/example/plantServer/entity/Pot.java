package com.example.plantServer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="pot")
@NoArgsConstructor
public class Pot {

    @Id
    @Column(name="serial_Id")
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

    @Column(name="wateringDate")
    private DateTime wateringDate;
}
