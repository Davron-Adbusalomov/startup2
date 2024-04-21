package com.example.startUp2.controller;

import com.example.startUp2.dto.TariffDTO;
import com.example.startUp2.service.TariffService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tariff")
public class TariffController {

    @Autowired
    private TariffService tariffService;

    @PostMapping("/createTariff")
    public ResponseEntity<?> createTariff(@RequestBody TariffDTO tariffDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tariffService.createTariff(tariffDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("getAllTariff")
    public ResponseEntity<?> getAllTariffs(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tariffService.getAllTariffs());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("updateTariff/{id}")
    public ResponseEntity<?> updateTariff(@PathVariable Long id, @RequestBody TariffDTO tariffDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tariffService.updateTariff(tariffDTO, id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("deleteTariff/{id}")
    public ResponseEntity<?> deleteTariff(@PathVariable Long id, HttpServletResponse httpServletResponse){
        try {
            Cookie cookie = new Cookie("jwt", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            httpServletResponse.addCookie(cookie);
            return ResponseEntity.status(HttpStatus.OK).body(tariffService.deleteTariff(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("connectToTariff/{userId}/{tariffId}")
    public ResponseEntity<?> connectToTariff(@PathVariable Long userId, @PathVariable Long tariffId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tariffService.connectToTariff(userId, tariffId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("disconnectFromTariff/{userId}/{tariffId}")
    public ResponseEntity<?> disconnectFromTariff(@PathVariable Long userId, @PathVariable Long tariffId ){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tariffService.disconnectFromTariff(userId, tariffId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
