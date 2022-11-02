package com.example.plantServer.controller;


import com.example.plantServer.entity.Pot;
import com.example.plantServer.respository.PotRepository;
import com.example.plantServer.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pot")
public class PotController {

    private final PotRepository potRepository;
    private final S3Uploader s3Uploader;


    @GetMapping("/potMain")
    public List<Pot> potMain(){
        List<Pot> pot = potRepository.findAll();
        return potRepository.findAll();
    }

    @GetMapping("/potSetting")
    public Pot potSetting(@RequestBody HashMap<String, Object> pot) {

        log.info("pot={}", pot);
        return potRepository.findBySerialId(pot.get("serialId").toString());
    }

    @PostMapping("/potSetting")
    public Pot potSetting(@ModelAttribute UserPotSettingForm userPotSettingForm) throws IOException {

        log.info("pot={}", userPotSettingForm);

        String imageUrl = s3Uploader.upload(userPotSettingForm.getImageFile());

        Pot pot = potRepository.findBySerialId(userPotSettingForm.getSerialId());
        pot.setImageUrl(imageUrl);
        pot.setPotName(userPotSettingForm.getPotName());
        pot.setPlantName(userPotSettingForm.getPlantName());
        pot.setSerialId(userPotSettingForm.getSerialId());

        potRepository.updateUserSettingBySerialId(pot);

        System.out.println("imageUrl = " + imageUrl);

        return pot;
    }
}
