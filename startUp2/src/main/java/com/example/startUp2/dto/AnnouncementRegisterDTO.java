package com.example.startUp2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementRegisterDTO {
    private String description;

    private String gender;

    private Long author_id;

    private List<String> images;

    private String category_name;

    private Long price;
}
