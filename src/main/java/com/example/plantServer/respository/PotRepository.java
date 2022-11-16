package com.example.plantServer.respository;

import com.example.plantServer.entity.Plant;
import com.example.plantServer.entity.Pot;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PotRepository extends CrudRepository<Pot, String> {

    Pot findBySerialId(String name);

    void deleteBySerialId(String name);

    @Modifying // select 문이 아님을 나타낸다
    @Transactional
    @Query(value = "UPDATE pot q set q.pot_name = :#{#after.potName}, q.plant_name= :#{#after.plantName}, q.image_url = :#{#after.imageUrl} where q.serial_id = :#{#after.serialId}", nativeQuery = true)
    int updateUserSettingBySerialId(@Param("after") Pot afterPot);

    @Modifying
    @Transactional
    @Query(value="UPDATE pot q set q.water_Period = :#{#after.period} where q.serial_id = :#{#after.serialId}", nativeQuery = true)
    int updatePeriodBySerialId(@Param("after") Pot afterPot);

    @Modifying
    @Transactional
    @Query(value = "UPDATE pot q set q.humidity = :#{#sensor.humidity}, q.soil_humidity= :#{#sensor.Soil_humidity}, q.temper = :#{#sensor.temper}, q.water_level=:#{#sensor.waterLevel} where q.serial_id = :#{#sensor.serialId}", nativeQuery = true)
    int UpdateSensorData(@Param("sensor") Pot arduinoData);

}
