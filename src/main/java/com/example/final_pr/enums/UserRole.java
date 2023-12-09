package com.example.final_pr.enums;

public enum UserRole {

    ADMIN,USER;

    @Override
    public String toString() {
        return "ROLE_"+name();
    }
}
