package com.example.matchteam.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    private String email;
    private String name;
    private String password;
    private int studentNum;
    private String department;
    private String development;
}
