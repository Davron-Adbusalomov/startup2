package com.example.startUp2.dto;

import com.example.startUp2.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersRegisterDTO {

    private String name;

    private String phone;

    private String country;

    private String password;

    private Role role;
}
