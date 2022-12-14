package com.example.plantServer.controller;


import com.example.plantServer.entity.Pot;
import com.example.plantServer.entity.WateringLog;
import com.example.plantServer.form.ArduinoData;
import com.example.plantServer.form.UserPotSettingForm;
import com.example.plantServer.form.WateringPeriodForm;
import com.example.plantServer.respository.PotRepository;
import com.example.plantServer.respository.WateringLogRepository;
import com.example.plantServer.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.sql.In;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pot")
public class PotController {

    public static final Integer Watering_Request = 21;
    public static final Integer Server_Response = 20;
    public static final int Watering_Check = 11;

    private final PotRepository potRepository;
    private final WateringLogRepository wateringLogRepository;
    private final S3Uploader s3Uploader;
    public static Map<String, Integer> statusMap = new ConcurrentHashMap<>();

    @GetMapping("/init")
    @PostConstruct
    public void arduinoStatusSetting() {
        statusMap = new ConcurrentHashMap<>();
        List<Pot> myList = new ArrayList<>();
        potRepository.findAll().forEach(myList::add);
        for (Pot pot : myList) {
            statusMap.put(pot.getSerialId(), Server_Response);
        }
    }

    @GetMapping("/status")
    public Map<String, Integer> statusCheck() {
        return statusMap;
    }

    @DeleteMapping("/{serialId}")
    public String deleteSerialId(@PathVariable("serialId") String serialId) {
        if (serialId.equals("empty")) {
            log.info("serialId is Empty");
            return "not available";
        }
        potRepository.deleteBySerialId(serialId);
        statusMap.remove(serialId);
        log.info("serialId deleted = {}", serialId);
        return serialId + " is deleted";
    }

    @GetMapping("/{serialId}")
    public String addSerialId(@PathVariable("serialId") String serialId) {
        if (serialId.equals("empty")) {
            log.info("serialId is Empty");
            String serialNumber = UUID.randomUUID().toString();

            Pot addPot = new Pot();
            addPot.setSerialId(serialNumber);

            potRepository.save(addPot);

            statusMap.put(serialNumber, Server_Response);

            return serialNumber;

        }
        log.info("serialId is exist : {}", serialId);
        return serialId;
    }

    @PostMapping("/addPot")
    public Pot addPot(@RequestBody HashMap<String, String> pot) {

        List<Pot> myList = new ArrayList<>();
        potRepository.findAll().forEach(myList::add);

        for (Pot tempPot : myList) {
            if (tempPot.getSerialId().equals(pot.get("serialId"))) {
                return tempPot;
            }
        }
        return null;
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
        log.info("afterPot={}", pot);

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
    @GetMapping("/{id}/info")
    public Pot getPotInfo(@PathVariable("id") String id) {
        log.info("id={}", id);
        Pot result = potRepository.findBySerialId(id);
        return result;
    }
    @PutMapping("/{id}/modify")
    public String setPeriodByArduino(@PathVariable("id") String id, @RequestBody HashMap<String, Object> term) {
        log.info("term={}", term);
        Pot result = potRepository.findBySerialId(id);
        result.setPeriod((Integer)term.get("water_term"));
        potRepository.updatePeriodBySerialId(result);
        return "200 OK";
    }
    @PostMapping("/{id}/sensor_data")
    public Map<String, Object> updateSensorData(@PathVariable("id") String id, @RequestBody ArduinoData arduinoData) {
        log.info("sensor-data={}", arduinoData);

        Pot pot = getArduinoData(id, arduinoData);
        potRepository.UpdateSensorData(pot);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("serialId", id);
        responseMap.put("serverStatus", "200 OK");

        log.info("response json ={}", responseMap);
        return responseMap;
    }
//    @PostMapping("/{id}/sensor-data")
//    public Map<String, Object> updateSensorData1(@PathVariable("id") String id, @RequestBody ArduinoData arduinoData) {
//        log.info("sensor-data={}", arduinoData);
//
//        Pot pot = getArduinoData(id, arduinoData);
//
//        potRepository.UpdateSensorData(pot);
//
//        if (arduinoData.getStatus() == Watering_Check) {
//            WateringLog wateringLog = new WateringLog(pot.getSerialId(), LocalDateTime.now().minusSeconds(1));
//            wateringLogRepository.save(wateringLog);
//            log.info("add Watering Log : {}", LocalDateTime.now().minusSeconds(1));
//        }
//
//        if (statusMap.get(id).equals(Watering_Request)) {
//            Map<String, Object> responseMap = new HashMap<>();
//            responseMap.put("serialId", id);
//            responseMap.put("serverStatus", statusMap.get(pot.getSerialId()));
//            return responseMap;
//        }
//
//        Pot checkPot = potRepository.findBySerialId(pot.getSerialId());
//        List<WateringLog> wateringDates = checkPot.getWateringDates();
//        Integer period = checkPot.getPeriod();
//
//        if (period != null && wateringDates.size() != 0) {
//
//            LocalDateTime lastDate = wateringDates.get(wateringDates.size() - 1).getWateringDate();
//            LocalDateTime now = LocalDateTime.now();
//
//            if (lastDate.plusSeconds(checkPot.getPeriod() / 1000).equals(now)) {
//                statusMap.put(checkPot.getSerialId(), Watering_Request);
//                log.info("WateringLog={}", LocalDateTime.now());
//            } else {
//                statusMap.put(checkPot.getSerialId(), Server_Response);
//            }
//        }
//        statusMap.put(checkPot.getSerialId(), Server_Response);
//
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("serialId", checkPot.getSerialId());
//        responseMap.put("serverStatus", statusMap.get(checkPot.getSerialId()));
//
//        log.info("response json ={}", responseMap);
//        return responseMap;
//    }

    private Pot getArduinoData(String id, ArduinoData arduinoData) {
        Pot pot = new Pot();
        pot.setSerialId(id);
        pot.setWaterLevel(arduinoData.getWaterLevel());
        pot.setTemper(Math.round(arduinoData.getTemper()));
        pot.setHumidity(Math.round(arduinoData.getHumidity()));
        pot.setSoil_humidity(arduinoData.getSoil_humidity());
        return pot;
    }

    @PostMapping("/watering")
    public String updateSensorData(@RequestBody HashMap<String, String> potId) {
        String serialID = potId.get("serialId");
        statusMap.put(serialID, Watering_Request);
        return "요청 완료";
    }


}
