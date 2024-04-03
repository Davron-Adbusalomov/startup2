package com.example.startUp2.repository;

import com.example.startUp2.model.Tariff;
import com.example.startUp2.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByPhone(String phone);

    Optional<Users> findByUsername(String username);

    Optional<List<Users>> findByTariff(Tariff tariff);
}
