package com.example.plantServer.respository;

import com.example.plantServer.entity.WateringLog;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface WateringLogRepository extends CrudRepository<WateringLog, String> {
}
