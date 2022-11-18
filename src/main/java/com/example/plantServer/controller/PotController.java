package com.example.plantServer.controller;


import com.example.plantServer.entity.Pot;
import com.example.plantServer.entity.WateringLog;
import com.example.plantServer.respository.PotRepository;
import com.example.plantServer.respository.WateringLogRepository;
import com.example.plantServer.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pot")
public class PotController {

    private final PotRepository potRepository;
    private final WateringLogRepository wateringLogRepository;
    private final S3Uploader s3Uploader;


    @PostMapping("/addPot")
    public Pot addPot(@RequestBody HashMap<String, String> pot) {
        Pot savePot = new Pot();
        savePot.setSerialId(pot.get("serialId"));
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//savePot.setWateringDate(timestamp);
        potRepository.save(savePot);
        return savePot;
    }
    @GetMapping("/potMain")
    public List<Pot> potMain() {
        List<Pot> myList = new ArrayList<>();
        potRepository.findAll().forEach(myList::add);
        return myList;
    }

    @GetMapping("/potSetting")
    public Pot potSetting(@RequestBody HashMap<String, Object> pot) {

        log.info("pot={}", pot);
        System.out.println(pot.get("serialId").toString());
        return potRepository.findBySerialId(pot.get("serialId").toString());
    }

    @PostMapping("/potSetting")
    public Pot potSetting(@ModelAttribute UserPotSettingForm userPotSettingForm) throws IOException {
        log.info("pot={}", userPotSettingForm);

        Pot pot = new Pot();
        pot.setPotName(userPotSettingForm.getPotName());
        pot.setPlantName(userPotSettingForm.getPlantName());
        pot.setSerialId(userPotSettingForm.getSerialId());

        String imageUrl = s3Uploader.upload(userPotSettingForm.getImageFile());
        pot.setImageUrl(imageUrl);
        log.info("pot={}", pot);

        potRepository.updateUserSettingBySerialId(pot);

        return pot;
    }

    @GetMapping("/setPeriod")
    public Pot setPeriod(@RequestBody HashMap<String, Object> pot) {
        log.info("pot={}", pot);
        return potRepository.findBySerialId(pot.get("serialId").toString());
    }

    @PostMapping("/setPeriod")
    public Pot setPeriod(@RequestBody WateringPeriodForm wateringPeriodForm) {
        log.info("pot={}", wateringPeriodForm);

        Pot afterPot = potRepository.findBySerialId(wateringPeriodForm.getSerialId());
        afterPot.setPeriod(wateringPeriodForm.getPeriod());

        potRepository.updatePeriodBySerialId(afterPot);

        return afterPot;
    }

    @GetMapping("/log")
    public List<WateringLog> setPeriod1(@RequestBody HashMap<String, Object> bb) {
        log.info("pot={}", bb);
        List<WateringLog> serialId = wateringLogRepository.findAllBySerialId(bb.get("serialId").toString());
        for (WateringLog wateringLog : serialId) {
            log.info("logList={}", wateringLog);
        }
        return serialId;
    }

    @GetMapping("/getSensorData")
    public String updateSensorData(@RequestBody ArduinoData arduinoData){
        log.info("pot={}", arduinoData);

        Pot pot = new Pot();
        pot.setSerialId(arduinoData.getSerialId());
        pot.setWaterLevel(arduinoData.getWaterLevel());
        pot.setTemper(arduinoData.getHumidity());
        pot.setHumidity(arduinoData.getHumidity());
        pot.setSoil_humidity(arduinoData.getSoil_humidity());

        potRepository.UpdateSensorData(pot);

        String status = arduinoData.getStatus();
        if (status.equals("watering")) {
            WateringLog wateringLog = new WateringLog(potRepository.findBySerialId(pot.getSerialId()), LocalDateTime.now());
            wateringLogRepository.save(wateringLog);
        }
        return "g";
    }
}
