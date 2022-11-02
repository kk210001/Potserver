package com.example.plantServer.respository;

import com.example.plantServer.entity.Plant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PlantRepository extends CrudRepository<Plant, String> {

    Plant findByName(String name);

    void deleteByName(String name);

    @Modifying // select 문이 아님을 나타낸다
    @Transactional
    @Query(value = "UPDATE plants p set p.refer_humidity = :#{#afterPlant.humidity}, p.water_period = :#{#afterPlant.period}, p.refer_temper= :#{#afterPlant.temper}, p.refer_sunlight = :#{#afterPlant.sunlight} where p.plant_name = :name", nativeQuery = true)
    int updateName(String name, Plant afterPlant);

}
