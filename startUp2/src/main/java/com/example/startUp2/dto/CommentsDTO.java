package com.example.startUp2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {
    private Long id;

    private String message;

    private String author;

    private long announcementId;
}
