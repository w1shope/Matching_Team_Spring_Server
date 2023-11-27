package com.example.matchteam.domain.board;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Board {
    private Long id;
    private String title;
    private String content;
    private int viewCnt;
    private Timestamp createdDate;
    private int statusId;

    @Builder
    public Board(Long id, String title, String content, int viewCnt, Timestamp createdDate, int statusId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCnt = viewCnt;
        this.createdDate = createdDate;
        this.statusId = statusId;
    }
}
