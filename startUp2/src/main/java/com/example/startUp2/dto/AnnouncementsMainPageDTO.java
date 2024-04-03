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
public class AnnouncementsMainPageDTO {
    private long id;

    private String description;

    private List<String> images;

    private long price;

    private LocalDate date;
}
