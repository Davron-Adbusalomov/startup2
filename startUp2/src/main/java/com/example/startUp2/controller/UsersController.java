package com.example.startUp2.controller;

import com.example.startUp2.dto.UsersDTO;
import com.example.startUp2.dto.UsersLoginDTO;
import com.example.startUp2.dto.UsersRegisterDTO;
import com.example.startUp2.service.UsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UsersRegisterDTO usersRegisterDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usersService.registerUser(usersRegisterDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("loginUser")
    public ResponseEntity<?> loginUser(@RequestBody UsersLoginDTO usersLoginDTO, HttpServletResponse response){
        try{
            String token = usersService.loginUser(usersLoginDTO);
            Cookie cookie = new Cookie("jwt", token);
            cookie.setMaxAge(86400);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully login");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<?> logoutUser(HttpServletResponse httpServletResponse){
        Cookie cookie = new Cookie("jwt", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully logout!");
    }

    @PutMapping("editProfile/{id}")
    public ResponseEntity<?> editProfile(@PathVariable Long id, @RequestBody UsersDTO usersDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usersService.editProfile(usersDTO,id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("deleteUser/{id}")
    @Cascade(CascadeType.ALL)
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usersService.deleteUser(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
