package com.example.startUp2.service;

import com.example.startUp2.dto.TariffDTO;
import com.example.startUp2.model.Tariff;
import com.example.startUp2.model.Users;
import com.example.startUp2.repository.TariffRepository;
import com.example.startUp2.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TariffService {

    @Autowired
    private TariffRepository tariffRepository;

    @Autowired
    private UsersRepository usersRepository;

    public String createTariff(TariffDTO tariffDTO){
        Tariff tariff = new Tariff();
        tariff.setDefinition(tariffDTO.getDefinition());
        tariffRepository.save(tariff);
        return "Successfully saved!";
    }

    public List<TariffDTO> getAllTariffs(){
        List<TariffDTO> tariffDTOList = new ArrayList<>();
        var tariffs = tariffRepository.findAll();
        for (Tariff tariff:tariffs) {
            TariffDTO tariffDTO = new TariffDTO();
            tariffDTO.setDefinition(tariff.getDefinition());
            tariffDTOList.add(tariffDTO);
        }
        return tariffDTOList;
    }

    public String updateTariff(TariffDTO tariffDTO, Long id){
        Optional<Tariff> tariff = tariffRepository.findById(id);
        if (tariff.isEmpty()){
            throw new EntityNotFoundException("No tariff found with this id");
        }

        tariff.get().setDefinition(tariffDTO.getDefinition());
        tariffRepository.save(tariff.get());
        return "Successfully updated!";
    }

    public String deleteTariff(Long id){
        Optional<Tariff> tariff = tariffRepository.findById(id);
        if (tariff.isEmpty()){
            throw new EntityNotFoundException("No tariff found with this id");
        }

        Optional<List<Users>> users = usersRepository.findByTariff(tariff.get());

        if (users.isPresent()){
            for (Users u:users.get()) {
                u.setTariff(null);
                usersRepository.save(u);
            }
        }

        tariffRepository.delete(tariff.get());
        return "Successfully deleted!";
    }

    public String connectToTariff(Long userId, Long tariffId){
        Optional<Users> users = usersRepository.findById(userId);
        if (users.isEmpty()){
            throw new EntityNotFoundException("No user found with this id");
        }

        Optional<Tariff> tariff = tariffRepository.findById(tariffId);
        if (tariff.isEmpty()){
            throw new EntityNotFoundException("No tariff found with this id");
        }

        tariff.get().getUsers().add(users.get());
        tariffRepository.save(tariff.get());

        return "Successfully connected!";
    }

    public String disconnectFromTariff(Long userId, Long tariffId){
        Optional<Users> users = usersRepository.findById(userId);
        if (users.isEmpty()){
            throw new EntityNotFoundException("No user found with this id");
        }

        Optional<Tariff> tariff = tariffRepository.findById(tariffId);
        if (tariff.isEmpty()){
            throw new EntityNotFoundException("No tariff found with this id");
        }

        tariff.get().getUsers().remove(users.get());
        tariffRepository.save(tariff.get());

        return "Successfully disconnected!";
    }

}
