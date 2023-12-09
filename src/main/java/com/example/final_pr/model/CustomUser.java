package com.example.final_pr.model;

import javax.persistence.*;
import com.example.final_pr.dto.CustomUserDTO;
import com.example.final_pr.enums.UserRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CustomUser {

    @Id
    @GeneratedValue
    private Long id;

    private String password;
    private String login;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String numberPhone;

    public CustomUser(String password, String login, UserRole role, String numberPhone){
        this.password = password;
        this.login = login;
        this.role = role;
        this.numberPhone= numberPhone;
    }

    public static CustomUser of(String password, String login, UserRole role, String numberPhone){
        return new CustomUser(password,login,role,numberPhone);
    }

    public CustomUserDTO toDTO(){
        return CustomUserDTO.of(password,login,role,numberPhone);
    }

    public static CustomUser fromDTO(CustomUserDTO customUserDTO){
        return CustomUser.of(customUserDTO.getPassword(), customUserDTO.getLogin(),
                customUserDTO.getRole(), customUserDTO.getNumberPhone());
    }

}
