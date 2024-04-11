package com.example.startUp2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDTO {
    private Long id;

    private String description;

    private String gender;

    private String author_telegram;

    private String author_whatsapp;

    private String author_phone;

    private String author_name;

    private Long price;

    private LocalDate date;

    private List<String> images;
}
