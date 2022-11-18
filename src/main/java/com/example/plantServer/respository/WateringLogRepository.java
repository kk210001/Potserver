package com.example.plantServer.respository;

import com.example.plantServer.entity.Pot;
import com.example.plantServer.entity.WateringLog;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WateringLogRepository extends CrudRepository<WateringLog, String> {

    List<WateringLog> findAllBySerialId(String name);
}
