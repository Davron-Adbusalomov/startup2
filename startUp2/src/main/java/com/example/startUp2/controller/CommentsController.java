package com.example.startUp2.controller;

import com.example.startUp2.dto.CommentsDTO;
import com.example.startUp2.dto.TariffDTO;
import com.example.startUp2.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @PostMapping("/createComment")
    public ResponseEntity<?> createComment(@RequestBody CommentsDTO commentsDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentsService.createComment(commentsDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("getComments/{announcementId}")
    public ResponseEntity<?> getComments(@PathVariable Long announcementId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentsService.getCommentsByAnnounceId(announcementId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("deleteComment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentsService.deleteComment(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
