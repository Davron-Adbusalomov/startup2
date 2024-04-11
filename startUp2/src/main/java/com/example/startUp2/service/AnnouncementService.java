package com.example.startUp2.service;

import com.example.startUp2.dto.AnnouncementDTO;
import com.example.startUp2.dto.AnnouncementRegisterDTO;
import com.example.startUp2.dto.AnnouncementsMainPageDTO;
import com.example.startUp2.model.*;
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
import java.util.stream.Collectors;

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
        System.out.println(announcement.getImages());
        return "Successfully saved";
    }

    public List<AnnouncementsMainPageDTO> getAllTops(Long categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) throw new EntityNotFoundException("No category found with this Id");

        List<Announcement> announcements = announcementRepository.findByCategoryIs(category.get());

        List<Announcement> tops = announcements.stream().filter(Announcement::is_top).toList();

        List<AnnouncementsMainPageDTO> announcementsMainPageDTOS = new ArrayList<>();

        for (Announcement ann : tops) {
            announcementsMainPageDTOS.add(toMainPageDTO(ann));
        }

        return announcementsMainPageDTOS;
    }

    public List<AnnouncementsMainPageDTO> getAllRegulars(Long categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) throw new EntityNotFoundException("No category found with this Id");

        List<Announcement> announcements = announcementRepository.findByCategoryIs(category.get());

        List<Announcement> regulars = announcements.stream().filter(a -> !a.is_top()).toList();

        List<AnnouncementsMainPageDTO> announcementsMainPageDTOS = new ArrayList<>();

        for (Announcement ann : regulars) {
            announcementsMainPageDTOS.add(toMainPageDTO(ann));
        }

        return announcementsMainPageDTOS;
    }



    public AnnouncementDTO getAnnouncementDetails(Long id){
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if (announcement.isEmpty()){
            throw new EntityNotFoundException("No user with this id");
        }

        Announcement announcement1 = announcement.get();
        return toDTO(announcement1);
    }

    public String deleteAnnouncement(Long id){
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if (announcement.isEmpty()){
            throw new EntityNotFoundException("No announcement with this id");
        }

        for (Comments c: announcement.get().getComments()) {
            c.setAnnouncement(null);
        }

        announcementRepository.delete(announcement.get());
        return "Successfully deleted!";
    }

    private AnnouncementDTO toDTO(Announcement announcement) {
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setId(announcement.getId());
        announcementDTO.setDescription(announcement.getDescription());
        announcementDTO.setGender(announcement.getGender());
        announcementDTO.setPrice(announcement.getPrice());
        announcementDTO.setDate(announcement.getDate());
        announcementDTO.setAuthor_name(announcement.getAuthor().getName());
        announcementDTO.setAuthor_phone(announcement.getAuthor().getPhone());
        announcementDTO.setAuthor_telegram(announcement.getAuthor().getTelegram());
        announcementDTO.setAuthor_whatsapp(announcement.getAuthor().getWhatsapp());
        announcementDTO.setImages(announcement.getImages().stream().map(Announcement_media::getUrl).collect(Collectors.toList()));
        return announcementDTO;
    }

    private AnnouncementsMainPageDTO toMainPageDTO(Announcement announcement) {
        AnnouncementsMainPageDTO announcementsMainPageDTO = new AnnouncementsMainPageDTO();
        announcementsMainPageDTO.setId(announcement.getId());
        announcementsMainPageDTO.setPrice(announcement.getPrice());
        announcementsMainPageDTO.setDate(announcement.getDate());
        announcementsMainPageDTO.setDescription(announcement.getDescription());
        announcementsMainPageDTO.setImages(announcement.getImages().stream().map(Announcement_media::getUrl).collect(Collectors.toList()));
        return announcementsMainPageDTO;
    }

}
