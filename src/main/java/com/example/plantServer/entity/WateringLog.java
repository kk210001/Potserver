package com.example.plantServer.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "watering_log")
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class WateringLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serial_id",insertable = false,updatable = false)
    private Pot pot;

    @Column(name = "serial_id")
    private String serialId;



    @Column(name="watering_Date")
    private LocalDateTime wateringDate;

    public WateringLog(Pot pot, LocalDateTime wateringDate) {
        this.pot = pot;
        this.wateringDate = wateringDate;
    }

}
