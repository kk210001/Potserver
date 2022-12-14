package com.example.plantServer.controller;

import com.example.plantServer.entity.Pot;
import com.example.plantServer.entity.WateringLog;
import com.example.plantServer.respository.PotRepository;
import com.example.plantServer.respository.WateringLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ManageController {
    private final PotRepository potRepository;
    private final WateringLogRepository wateringLogRepository;

    @GetMapping("/condition")
    public String condition(Model model) {
        List<Pot> potList = new ArrayList<>();
        potRepository.findAll().forEach(potList::add);
        model.addAttribute("pots", potList);

        log.info("list={}",potList);
        return "manage/condition";

    }
    @GetMapping("/delete/{serialId}")
    public String deleteSerialId(@PathVariable("serialId") String serialId) {
        potRepository.deleteBySerialId(serialId);
        log.info("serialId deleted = {}", serialId);
        return "redirect:/condition";
    }
    @GetMapping("/add")
    public String addSerialId() {

            String serialNumber = UUID.randomUUID().toString();

            Pot addPot = new Pot();
            addPot.setSerialId(serialNumber);

            potRepository.save(addPot);

            return "redirect:/pot/init";

    }
    @GetMapping("/add_log/{serialId}")
    public String addWateringLog(@PathVariable("serialId") String serialId) {

        WateringLog wateringLog = new WateringLog(serialId, LocalDateTime.now());
        log.info("wateringLog = {}", wateringLog);
        wateringLogRepository.save(wateringLog);

        return "redirect:/condition";

    }

}
