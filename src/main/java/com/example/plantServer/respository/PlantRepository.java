package com.example.plantServer.respository;

import com.example.plantServer.entity.Plant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface PlantRepository extends CrudRepository<Plant, String> {

    Plant findByName(String name);

    void deleteByName(String name);

    @Modifying // select 문이 아님을 나타낸다
    @Transactional
    @Query(value = "UPDATE plants set refer_humidity = :#{#plant.humidity}, water_period = :#{#plant.period}, refer_temper= :#{#plant.temper}, refer_sunlight = :#{#plant.sunlight} where plant_name = :#{#plant.name}", nativeQuery = true)
    int updateName(@Param("plant") Plant afterPlant);

}
