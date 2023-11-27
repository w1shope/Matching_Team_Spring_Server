package com.example.matchteam.dto.board.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnrolCommentDto {
    private String commentContent;
    private String email;
    private String boardTitle;
    private String boardContent;

    @Builder
    public EnrolCommentDto(String commentContent, String email, String boardTitle, String boardContent) {
        this.commentContent = commentContent;
        this.email = email;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}
