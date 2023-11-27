package com.example.matchteam.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBoardDto {
    private String oldTitle;
    private String oldContent;
    private String newTitle;
    private String newContent;
}
