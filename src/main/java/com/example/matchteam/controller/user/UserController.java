package com.example.matchteam.controller.user;

import com.example.matchteam.domain.user.User;
import com.example.matchteam.dto.board.FindUserDto;
import com.example.matchteam.dto.user.CreateUserDto;
import com.example.matchteam.dto.user.WriteCountDto;
import com.example.matchteam.service.board.BoardService;
import com.example.matchteam.service.board.comment.CommentService;
import com.example.matchteam.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("/users")
    public Long saveUser(@RequestBody CreateUserDto createUserDto) {
        return userService.saveUser(createUserDto);
    }

    @GetMapping("/users")
    public User findByUserEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return userService.findByUserEmailAndPassword(email, password);
    }

    @GetMapping("/users/{email}/{password}")
    public FindUserDto userInfo(@PathVariable("email") String email, @PathVariable("password") String password) {
        FindUserDto userInfo = userService.findUserInfo(email);
        return userInfo;
    }

    @GetMapping("/users/{email}")
    public WriteCountDto getWriteCount(@PathVariable String email) {
        return new WriteCountDto(boardService.getWriteCount(email), commentService.getWriteCount(email));
    }
    @GetMapping("/users/exist/{email}")
    public boolean existUser(@PathVariable String email) {
        return userService.exist(email);
    }
}
