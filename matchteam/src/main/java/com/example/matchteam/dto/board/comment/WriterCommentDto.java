package com.example.matchteam.dto.board.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriterCommentDto {
    private String name;

    public WriterCommentDto(String name) {
        this.name = name;
    }
}
