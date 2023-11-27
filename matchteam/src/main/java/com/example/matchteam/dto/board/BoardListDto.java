package com.example.matchteam.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BoardListDto {
    private Long id;
    private String title;
    private String content;
    private int viewCnt;
    private Timestamp createdDate;
    private int status;

    @Builder

    public BoardListDto(Long id, String title, String content, int viewCnt, Timestamp createdDate, int status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCnt = viewCnt;
        this.createdDate = createdDate;
        this.status = status;
    }
}