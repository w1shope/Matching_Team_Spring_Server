package com.example.matchteam.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteCountDto {
    private int boardCount;
    private int commentCount;

    public WriteCountDto(int boardCount, int commentCount) {
        this.boardCount = boardCount;
        this.commentCount = commentCount;
    }
}
