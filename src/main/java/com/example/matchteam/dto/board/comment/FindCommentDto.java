package com.example.matchteam.dto.board.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class FindCommentDto {
    private String content;
    private String name;
    private Timestamp createdDate;

    @Builder
    public FindCommentDto(String content, String name, Timestamp createdDate) {
        this.content = content;
        this.name = name;
        this.createdDate = createdDate;
    }
}
