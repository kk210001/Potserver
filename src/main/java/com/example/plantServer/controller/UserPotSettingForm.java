package com.example.plantServer.controller;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserPotSettingForm {
    private String serialId;
    private String potName;
    private String plantName;
    private MultipartFile imageFile;
}
