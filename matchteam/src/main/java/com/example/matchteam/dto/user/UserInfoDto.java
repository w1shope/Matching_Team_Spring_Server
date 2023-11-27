package com.example.matchteam.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private String email;
    private String name;
    private String department;
    private int studentNum;

    @Builder
    public UserInfoDto(String email, String name, String department, int studentNum) {
        this.email = email;
        this.name = name;
        this.department = department;
        this.studentNum = studentNum;
    }
}
