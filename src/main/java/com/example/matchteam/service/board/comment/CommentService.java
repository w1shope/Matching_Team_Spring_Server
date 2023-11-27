package com.example.matchteam.service.board.comment;

import com.example.matchteam.dto.board.BoardListDto;
import com.example.matchteam.dto.board.comment.EnrolCommentDto;
import com.example.matchteam.dto.board.comment.FindCommentDto;
import com.example.matchteam.repository.board.comment.CommentRepository;
import com.example.matchteam.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Transactional
    public String enrol(EnrolCommentDto dto) {
        Long userId = commentRepository.enrol(dto);
        return userService.findByUserName(userId);
    }

    @Transactional
    public FindCommentDto find(Long boardId) {
        return commentRepository.find(boardId);
    }

    @Transactional
    public boolean delete(String content, String email) {
        return commentRepository.delete(content, email);
    }

    @Transactional
    public List<BoardListDto> findAll(String email) {
        return commentRepository.findAll(email);
    }

    @Transactional
    public int getWriteCount(String email) {
        return commentRepository.getWriteCount(email);
    }
}
