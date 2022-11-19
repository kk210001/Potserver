package com.example.plantServer.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "watering_log")
@NoArgsConstructor
@JsonIgnoreProperties("id")
public class WateringLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @JsonIgnore
    @Column(name = "serial_id")
    private String serialId;



    @Column(name="watering_Date")
    private LocalDateTime wateringDate;

    public WateringLog(String serialId, LocalDateTime wateringDate) {
        this.serialId = serialId;
        this.wateringDate = wateringDate;
    }

}
