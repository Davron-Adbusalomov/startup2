package com.example.startUp2.repository;

import com.example.startUp2.model.Announcement;
import com.example.startUp2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByCategoryIs(Category category);
}
