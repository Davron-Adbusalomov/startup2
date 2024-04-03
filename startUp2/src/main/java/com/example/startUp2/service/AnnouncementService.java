package com.example.startUp2.service;

import com.example.startUp2.dto.AnnouncementDTO;
import com.example.startUp2.dto.AnnouncementRegisterDTO;
import com.example.startUp2.dto.AnnouncementsMainPageDTO;
import com.example.startUp2.model.Announcement;
import com.example.startUp2.model.Announcement_media;
import com.example.startUp2.model.Category;
import com.example.startUp2.model.Users;
import com.example.startUp2.repository.AnnouncementMediaRepository;
import com.example.startUp2.repository.AnnouncementRepository;
import com.example.startUp2.repository.CategoryRepository;
import com.example.startUp2.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AnnouncementMediaRepository announcementMediaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public String createAnnouncement(AnnouncementRegisterDTO announcementRegisterDTO){
        Optional<Users> user = usersRepository.findById(announcementRegisterDTO.getAuthor_id());
        if (user.isEmpty()){
            throw new EntityNotFoundException("No user with this id");
        }
        Optional<Category> category = categoryRepository.findByName(announcementRegisterDTO.getCategory_name());
        if (category.isEmpty()){
            throw new EntityNotFoundException("No category with this name");
        }

        LocalDate localDate = LocalDate.now();

        Announcement announcement = new Announcement();
        announcement.setDescription(announcementRegisterDTO.getDescription());
        announcement.setGender(announcementRegisterDTO.getGender());
        announcement.setAuthor(user.get());
        announcement.setDate(localDate);
        announcement.setPrice(announcementRegisterDTO.getPrice());
        if (!announcementRegisterDTO.getImages().isEmpty()){
            List<Announcement_media> announcementMedias = new ArrayList<>();
            for (String s:announcementRegisterDTO.getImages()) {
                Announcement_media announcementMedia = new Announcement_media();
                announcementMedia.setUrl(s);
                announcementMediaRepository.save(announcementMedia);
                announcementMedias.add(announcementMedia);
            }
            announcement.setImages(announcementMedias);
        }
        announcement.setCategory(category.get());

        announcementRepository.save(announcement);

        return "Successfully saved";
    }

//    public AnnouncementsMainPageDTO getAll(){
//
//    }

    public AnnouncementDTO getAnnouncementDetails(Long id){
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if (announcement.isEmpty()){
            throw new EntityNotFoundException("No user with this id");
        }

        Announcement announcement1 = announcement.get();
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setId(announcement1.getId());
        announcementDTO.setDescription(announcement1.getDescription());
        announcementDTO.setGender(announcement1.getGender());
        announcementDTO.setPrice(announcement1.getPrice());
        announcementDTO.setDate(announcement1.getDate());
        announcementDTO.setAuthor_name(announcement1.getAuthor().getName());
        announcementDTO.setAuthor_phone(announcement1.getAuthor().getPhone());
        announcementDTO.setAuthor_telegram(announcement1.getAuthor().getTelegram());
        announcementDTO.setAuthor_whatsapp(announcement1.getAuthor().getWhatsapp());
        List<String> mediaList = new ArrayList<>();
        for (Announcement_media a:announcement.get().getImages()) {
            mediaList.add(a.getUrl());
        }
        announcementDTO.setImages(mediaList);
        return announcementDTO;
    }

    public String deleteAnnouncement(Long id){
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if (announcement.isEmpty()){
            throw new EntityNotFoundException("No announcement with this id");
        }

        announcementRepository.delete(announcement.get());
        return "Successfully deleted!";
    }


}
