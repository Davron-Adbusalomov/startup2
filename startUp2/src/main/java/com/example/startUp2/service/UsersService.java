package com.example.startUp2.service;

import com.example.startUp2.config.JwtService;
import com.example.startUp2.dto.UsersDTO;
import com.example.startUp2.dto.UsersLoginDTO;
import com.example.startUp2.dto.UsersRegisterDTO;
import com.example.startUp2.model.Location;
import com.example.startUp2.model.Users;
import com.example.startUp2.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private final JwtService jwtService;

    public UsersService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String registerUser(UsersRegisterDTO usersRegisterDTO) throws Exception {
        if (usersRepository.findByPhone(usersRegisterDTO.getPhone()).isPresent()){
            throw new Exception("Phone already registered!");
        }
        Users users = new Users();
        users.setName(usersRegisterDTO.getName());
        users.setPhone(usersRegisterDTO.getPhone());
        users.setCountry(usersRegisterDTO.getCountry());
        users.setRole(usersRegisterDTO.getRole());
        users.setUsername(usersRegisterDTO.getPhone());
        users.setPassword(usersRegisterDTO.getPassword());
        usersRepository.save(users);
        return "Successfully saved!";
    }

    public String loginUser(UsersLoginDTO usersLoginDTO) throws Exception {
        Optional<Users> user = usersRepository.findByPhone(usersLoginDTO.getPhone());
        if (user.isEmpty()){
            throw new EntityNotFoundException("You do not have account with this number!");
        }
        if (!user.get().getPassword().equals(usersLoginDTO.getPassword())){
            throw new Exception("Password mismatch!");
        }

        return jwtService.generateToken(user.get());
    }

    public String editProfile(UsersDTO usersDTO, Long id){
        Optional<Users> users = usersRepository.findById(id);
        if (users.isEmpty()){
            throw new EntityNotFoundException("User not found with this id!");
        }

        Users editedUser = users.get();
        editedUser.setName(usersDTO.getName());
        editedUser.setCountry(usersDTO.getCountry());
        editedUser.setPassword(usersDTO.getPassword());
        editedUser.setPhone(usersDTO.getPhone());
        editedUser.setTelegram(usersDTO.getTelegram());
        editedUser.setWhatsapp(usersDTO.getWhatsapp());
        editedUser.setCard_number(usersDTO.getCard_number());
        if (usersDTO.getLocation().getLat()!=null){
            Location location = new Location();
            location.setLat(usersDTO.getLocation().getLat());
            location.setLon(usersDTO.getLocation().getLon());
            editedUser.setLocation(location);
        }
        if (!usersDTO.getPassword_img().isEmpty()){
            editedUser.setPassword_img(usersDTO.getPassword_img());
        }
        if (!usersDTO.getDriver_licence_img().isEmpty()){
            editedUser.setDriver_licence_img(usersDTO.getDriver_licence_img());
        }
        if (!usersDTO.getAvatar().isEmpty()){
            editedUser.setAvatar_img(usersDTO.getAvatar());
        }

        usersRepository.save(editedUser);
        return "Successfully edited";
    }

    public String deleteUser(Long id){
        Optional<Users> users = usersRepository.findById(id);
        if (users.isEmpty()){
            throw new EntityNotFoundException("No user with this id");
        }
        usersRepository.delete(users.get());
        return "Successfully deleted!";
    }

}
