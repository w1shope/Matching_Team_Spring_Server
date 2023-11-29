package com.example.matchteam.service.user;

import com.example.matchteam.domain.user.User;
import com.example.matchteam.dto.board.FindUserDto;
import com.example.matchteam.dto.user.CreateUserDto;
import com.example.matchteam.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User findByUserEmailAndPassword(String email, String password) {
        User findUser = userRepository.findByEmailAndEmail(email, password);
        return findUser == null ? null : findUser;
    }

    @Transactional
    public Long saveUser(CreateUserDto createUserDto) {
        return userRepository.saveUser(createUserDto);
    }

    @Transactional
    public Long findUserId(String email) {
        return userRepository.findUserId(email);
    }

    @Transactional
    public FindUserDto findUserInfo(String email) {
        return userRepository.findByUser(email);
    }

    @Transactional
    public String findByUserName(Long id) {
        return userRepository.findByUserName(id);
    }
    @Transactional
    public String getPassword(String email) {
        return userRepository.findPassword(email);
    }
    @Transactional
    public boolean exist(String email) {
        return userRepository.exist(email);
    }
}