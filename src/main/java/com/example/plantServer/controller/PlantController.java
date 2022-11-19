package com.example.plantServer.controller;

import com.example.plantServer.entity.Plant;
import com.example.plantServer.respository.PlantRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/plant")
public class PlantController {

    private final PlantRepository plantRepository;

    @GetMapping("/")
    public String getMainPage() {
        System.out.println("arduino = " + plantRepository);
        log.info("Arduino Access {}");
        System.out.println(System.getenv("USERNAME"));
        return "good";
    }


    @GetMapping("/findAll")
    public Iterable<Plant> findAll() {


        return plantRepository.findAll();
    }

    @GetMapping("/findName")
    public Plant findName(@RequestBody HashMap<String, String> plant) {
        log.info("plant={}", plant);
        return plantRepository.findByName(plant.get("name"));
    }

    @PostMapping("/addPlant")
    public Plant addPlant(@RequestBody String addPlant) {
        log.info("plant={}", addPlant);
        Gson gson = new Gson();
        Plant plant = gson.fromJson(addPlant, Plant.class);
        plantRepository.save(plant);
        return plant;
    }

    @PostMapping("/updatePlant")
    public Plant updatePlant(@RequestBody String addPlant) {
        log.info("plant={}", addPlant);
        Gson gson = new Gson();
        Plant plant = gson.fromJson(addPlant, Plant.class);
        if(plant.toString().contains("null")){
            return null;
        }
        plantRepository.updateName(plant);
        Plant byName = plantRepository.findByName(plant.getName());
        return byName;
    }


    @PostMapping("/deletePlant")
    public String delPlant(@RequestBody HashMap<String, String> plant) {
        log.info("plant={}", plant);
        plantRepository.deleteByName(plant.get("name"));
        return plant.get("name") + " 삭제 완료";
    }


}
