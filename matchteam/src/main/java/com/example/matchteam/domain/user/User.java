package com.example.matchteam.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private Long id;
    private String email;
    private String name;
    private int studentNum;
    private String password;
    private String department;
    private String development;

    @Builder
    public User(Long id, String email, String name, int studentNum, String password, String department, String development) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.studentNum = studentNum;
        this.password = password;
        this.department = department;
        this.development = development;
    }
}
