package com.example.startUp2.repository;

import com.example.startUp2.model.Announcement_media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementMediaRepository extends JpaRepository<Announcement_media, Long> {

}
