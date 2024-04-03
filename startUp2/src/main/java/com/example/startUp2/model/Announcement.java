package com.example.startUp2.model;

import com.example.startUp2.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Gender gender;

    private LocalDate date;

    private boolean is_top;

    private Long price;

    private int rate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "announcement")
    private List<Announcement_media> images;

    @OneToMany(mappedBy = "announcement")
    private List<Comments> comments;
}
