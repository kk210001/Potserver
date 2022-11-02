package com.example.plantServer.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plants")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plant {

    @Id
    @Column(name = "plant_name")
    private String name;

    @Column(name = "water_period")
    private String period;

    @Column(name = "refer_humidity")
    private String humidity;

    @Column(name = "refer_temper")
    private String temper;

    @Column(name = "refer_sunlight")
    private String sunlight;


    public Plant(String name, String period, String humidity, String temper, String sunlight) {
        this.name = name;
        this.period = period;
        this.humidity = humidity;
        this.temper = temper;
        this.sunlight = sunlight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemper() {
        return temper;
    }

    public void setTemper(String temper) {
        this.temper = temper;
    }

    public String getSunlight() {
        return sunlight;
    }

    public void setSunlight(String sunlight) {
        this.sunlight = sunlight;
    }

    @Override
    public String toString() {
        return "PlantsInfo{" +
                "name='" + name + '\'' +
                ", period='" + period + '\'' +
                ", humidity='" + humidity + '\'' +
                ", temper='" + temper + '\'' +
                ", sunlight='" + sunlight + '\'' +
                '}';
    }
}
