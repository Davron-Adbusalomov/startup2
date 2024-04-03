package com.example.startUp2.repository;

import com.example.startUp2.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<List<Comments>> findByAnnouncementId(Long announcementId);
}
