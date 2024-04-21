package com.example.startUp2.controller;

import com.example.startUp2.dto.AnnouncementRegisterDTO;
import com.example.startUp2.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/createAnnouncement")
    public ResponseEntity<?> createAnnouncement(@RequestBody AnnouncementRegisterDTO announcementRegisterDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(announcementService.createAnnouncement(announcementRegisterDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getAnnouncementDetails/{id}")
    public ResponseEntity<?> getAnnouncementById(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(announcementService.getAnnouncementDetails(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/GetTopAnnouncements/{categoryId}")
    public ResponseEntity<?> getTopAnnouncements(@PathVariable Long categoryId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(announcementService.getAllTops(categoryId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/GetRegularAnnouncements/{categoryId}")
    public ResponseEntity<?> getRegularAnnouncements(@PathVariable Long categoryId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(announcementService.getAllRegulars(categoryId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("deleteAnnouncement/{id}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(announcementService.deleteAnnouncement(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
