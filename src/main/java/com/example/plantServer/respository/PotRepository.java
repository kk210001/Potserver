package com.example.plantServer.respository;

import com.example.plantServer.entity.Plant;
import com.example.plantServer.entity.Pot;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PotRepository extends CrudRepository<Pot, String> {

    Pot findBySerialId(String name);

    void deleteBySerialId(String name);

    @Modifying // select 문이 아님을 나타낸다
    @Transactional
    @Query(value = "UPDATE pot p set p.pot_name = :#{#afterPot.potName}, p.plant_name= :#{#afterPot.plantName}, p.image_url = :#{#afterPot.imageUrl} where p.serial_id = :#{#afterPot.serialId}", nativeQuery = true)
    int updateUserSettingBySerialId(Pot afterPot);
}
