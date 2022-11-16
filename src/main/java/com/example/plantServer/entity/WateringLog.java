package com.example.plantServer.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "watering_log")
@NoArgsConstructor
public class WateringLog {

    @Id
    @Column(name = "serial_id")
    //    private String serialId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serial_id")
    private Pot pot;

    @Column(name="watering_Date")
    private LocalDateTime wateringDate;

    public WateringLog(Pot pot, LocalDateTime wateringDate) {
        this.pot = pot;
        this.wateringDate = wateringDate;
    }
}
