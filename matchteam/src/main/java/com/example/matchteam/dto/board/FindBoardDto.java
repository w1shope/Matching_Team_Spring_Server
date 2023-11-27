package com.example.matchteam.dto.board;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class FindBoardDto {
    private String title;
    private String content;
    private Timestamp createdDate;
    private int viewCnt;
}
