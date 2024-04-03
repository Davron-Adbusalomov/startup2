package com.example.startUp2.service;

import com.example.startUp2.dto.CommentsDTO;
import com.example.startUp2.model.Announcement;
import com.example.startUp2.model.Comments;
import com.example.startUp2.model.Users;
import com.example.startUp2.repository.AnnouncementRepository;
import com.example.startUp2.repository.CommentsRepository;
import com.example.startUp2.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    public String createComment(CommentsDTO commentsDTO){
        Optional<Users> users = usersRepository.findByUsername(commentsDTO.getAuthor());
        if (users.isEmpty()) throw new EntityNotFoundException("No user found with this username!");

        Optional<Announcement> announcement = announcementRepository.findById(commentsDTO.getAnnouncementId());
        if (announcement.isEmpty()) throw new EntityNotFoundException("No announcement found with this id!");

        Comments comments = new Comments();
        comments.setMessage(commentsDTO.getMessage());
        comments.setAnnouncement(announcement.get());
        comments.setUsers(users.get());

        commentsRepository.save(comments);

        return "Successfully saved!";
    }

    public List<CommentsDTO> getCommentsByAnnounceId(Long announcementId){
        Optional<Announcement> announcement = announcementRepository.findById(announcementId);
        if (announcement.isEmpty()) throw new EntityNotFoundException("No announcement found with this id!");

        var comments = commentsRepository.findByAnnouncementId(announcementId);
        var commentsDTOs = new ArrayList<CommentsDTO>();

        if (comments.isPresent()){
            for (Comments c:comments.get()) {
                CommentsDTO commentsDTO = new CommentsDTO();
                commentsDTO.setMessage(c.getMessage());
                commentsDTO.setAnnouncementId(c.getAnnouncement().getId());
                commentsDTO.setAuthor(c.getUsers().getUsername());
                commentsDTOs.add(commentsDTO);
            }
        }
        return commentsDTOs;
    }

    public String deleteComment(Long comment_id){
        Optional<Comments> comments = commentsRepository.findById(comment_id);
        if (comments.isEmpty()) throw new EntityNotFoundException("No comment found with this id!");

        commentsRepository.delete(comments.get());
        return "Successfully deleted!";
    }

}
