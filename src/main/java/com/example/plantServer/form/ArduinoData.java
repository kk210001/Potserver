package com.example.plantServer.form;


import lombok.Data;

@Data
public class ArduinoData {
//    private Integer status;
    private Float humidity;
//    private Integer humidity;
    private Integer waterLevel;
    private Integer soil_humidity;
//    private Integer temper;
    private Float temper;
}
