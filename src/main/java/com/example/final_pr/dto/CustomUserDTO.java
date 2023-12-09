package com.example.final_pr.dto;


import com.example.final_pr.enums.UserRole;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class CustomUserDTO {

    private String password;
    private String login;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String numberPhone;

    private CustomUserDTO(String password, String login, UserRole role, String numberPhone){
        this.password = password;
        this.login = login;
        this.role = role;
        this.numberPhone= numberPhone;
    }

    public static CustomUserDTO of(String password, String login, UserRole role, String numberPhone){
        return new CustomUserDTO(password, login, role, numberPhone);
    }
}
