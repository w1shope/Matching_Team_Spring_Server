package com.example.matchteam.dto.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDto {
    private String address; // 상대방 주소
    private String title; // 메일 제목
    private String message; // 메일 메시지
}
