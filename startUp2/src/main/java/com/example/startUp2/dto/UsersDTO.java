package com.example.startUp2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private String name;

    private String username;

    private String phone;

    private String country;

    private String telegram;

    private String whatsapp;

    private String password;

    private boolean is_active;

    private String card_number;

    private String password_img;

    private String driver_licence_img;

    private String avatar;

    private LocationDTO location;
}
