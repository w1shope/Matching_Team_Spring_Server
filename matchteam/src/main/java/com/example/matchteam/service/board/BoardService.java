package com.example.matchteam.service.board;

import com.example.matchteam.domain.board.Board;
import com.example.matchteam.dto.board.*;
import com.example.matchteam.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public List<BoardListDto> boards() {
        return boardRepository.boards();
    }

    @Transactional
    public List<BoardListDto> userBoards(String email) {
        return boardRepository.userBoards(email);
    }

    @Transactional
    public boolean enrol(EnrolBoardDto dto) {
        return boardRepository.enrol(dto);
    }

    @Transactional
    public Board findBoard(FindBoardDto dto) {
        return boardRepository.findBoard(dto);
    }

    @Transactional
    public boolean delete(DeleteBoardDto dto) {
        return boardRepository.delete(dto);
    }

    @Transactional
    public boolean update(UpdateBoardDto dto) {
        return boardRepository.update(dto);
    }

    @Transactional
    public Long findUserId(String title, String content) {
        return boardRepository.findUserId(title, content);
    }

    @Transactional
    public boolean updateBoardStatus(BoardStatusDto dto, int status) {
        return boardRepository.updateBoardStatus(dto, status);
    }

    @Transactional
    public Long findBoardId(String title, String content) {
        return boardRepository.findBoardId(title, content);
    }

    @Transactional
    public int getWriteCount(String email) {
        return boardRepository.getWriteCount(email);
    }
}
