package com.example.matchteam.controller.board;

import com.example.matchteam.domain.board.Board;
import com.example.matchteam.dto.board.*;
import com.example.matchteam.service.board.BoardService;
import com.example.matchteam.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @GetMapping("/boards")
    public List<BoardListDto> boards() {
        return boardService.boards();
    }

    @GetMapping("/boards/list/{email}")
    public List<BoardListDto> userBoards(@PathVariable String email) {
        return boardService.userBoards(email);
    }

    @GetMapping("/boards/read")
    public Board findBoard(@ModelAttribute FindBoardDto dto) {
        return boardService.findBoard(dto);
    }

    @PostMapping("/boards")
    public boolean enrolBoard(@RequestBody EnrolBoardDto dto) {
        return boardService.enrol(dto);
    }

    @DeleteMapping("/boards")
    public boolean deleteBoard(@ModelAttribute DeleteBoardDto dto) {
        boolean isSuccess = boardService.delete(dto);
        return isSuccess;
    }

    @PatchMapping("/boards")
    public boolean updateBoard(@RequestBody UpdateBoardDto dto) {
        boolean isSuccess = boardService.update(dto);
        return isSuccess;
    }

    @GetMapping("/boards/{email}")
    public boolean findByEmail(@PathVariable String email, @RequestParam String title, @RequestParam String content) {
        // 글을 작성한 사용자 id 가져오기
        Long boardWriteUserId = boardService.findUserId(title, content);
        // 이메일을 통해 사용자 id 가져오기
        Long userId = userService.findUserId(email);
        // 둘이 일치하는지 확인
        return boardWriteUserId == userId;
    }

    @PatchMapping("/boards/update")
    public boolean updateBoardStatus(@RequestBody BoardStatusDto dto, int status) {
        return boardService.updateBoardStatus(dto, status);
    }
}
