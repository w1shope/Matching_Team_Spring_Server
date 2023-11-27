package com.example.matchteam.controller.board.comment;

import com.example.matchteam.dto.board.BoardListDto;
import com.example.matchteam.dto.board.comment.EnrolCommentDto;
import com.example.matchteam.dto.board.comment.FindCommentDto;
import com.example.matchteam.service.board.BoardService;
import com.example.matchteam.service.board.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final BoardService boardService;

    @PostMapping("/comments")
    public String enrolComments(@RequestBody EnrolCommentDto dto) {
        return commentService.enrol(dto);
    }

    @GetMapping("/comments/{title}/{content}")
    public FindCommentDto findComment(@PathVariable String title, @PathVariable String content) {
        Long boardId = boardService.findBoardId(title, content);
        return commentService.find(boardId);
    }

    @DeleteMapping("/comments/{content}")
    public boolean deleteComment(@PathVariable String content, @RequestParam("loginEmail") String email) {
        return commentService.delete(content, email);
    }

    @GetMapping("/comments/{email}")
    public List<BoardListDto> commentAll(@PathVariable String email) {
        return commentService.findAll(email);
    }
}
