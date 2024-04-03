package com.example.startUp2.model;

import com.example.startUp2.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String phone;

    private String country;

    private String telegram;

    private String whatsapp;

    private String password;

    private boolean is_active;

    private String avatar_img;

    private String password_img;

    private String driver_licence_img;

    private String card_number;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    private Location location;

    @OneToMany(mappedBy = "author")
    private List<Announcement> announcement;

    @OneToMany(mappedBy = "users")
    private List<Comments> comments;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {return true;}
}
