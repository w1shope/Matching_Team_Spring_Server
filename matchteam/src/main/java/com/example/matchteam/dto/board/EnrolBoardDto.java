package com.example.matchteam.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrolBoardDto {
    private String title;
    private String content;
    private String email;
    private Long statusId;
}
