package com.example.matchteam.repository.user;

import com.example.matchteam.domain.user.User;
import com.example.matchteam.dto.board.FindUserDto;
import com.example.matchteam.dto.user.CreateUserDto;
import com.example.matchteam.dto.user.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@Slf4j
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long saveUser(CreateUserDto createUserDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            String sql = "INSERT INTO Users(email, name, password, student_num, department, development) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, createUserDto.getEmail());
            stmt.setString(2, createUserDto.getName());
            stmt.setString(3, createUserDto.getPassword());
            stmt.setInt(4, createUserDto.getStudentNum());
            stmt.setString(5, createUserDto.getDepartment());
            stmt.setString(6, createUserDto.getDevelopment());
            return stmt;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public User findByEmailAndEmail(String email, String password) {
        return jdbcTemplate.query("SELECT * FROM Users WHERE email = ? and password = ?",
                        (rs, rowNum) -> {
                            return User.builder()
                                    .id(rs.getLong("id"))
                                    .email(rs.getString("email"))
                                    .password(rs.getString("password"))
                                    .name(rs.getString("name"))
                                    .studentNum(rs.getInt("student_num"))
                                    .department(rs.getString("department"))
                                    .development(rs.getString("development"))
                                    .build();
                        }, email.trim(), password.trim())
                .stream().findFirst().get();
    }

    public UserInfoDto findUserInfo(String email) {
        return jdbcTemplate.query("SELECT * FROM Users WHERE email = ?",
                (rs, rowNum) -> {
                    return UserInfoDto.builder()
                            .email(rs.getString("email"))
                            .name(rs.getString("name"))
                            .department(rs.getString("department"))
                            .studentNum(rs.getInt("student_num"))
                            .build();
                }, email).stream().findFirst().get();
    }

    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?",
                (rs, rowNum) -> {
                    return User.builder()
                            .id(rs.getLong("id"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .name(rs.getString("name"))
                            .development(rs.getString("development"))
                            .studentNum(rs.getInt("student_num"))
                            .department(rs.getString("department"))
                            .build();
                }, email);
    }

    public FindUserDto findByUser(String email) {
        Long userId = findUserId(email);
        int writeCount = jdbcTemplate.queryForObject("SELECT COUNT(user_id) FROM boards WHERE user_id = ?",
                Integer.class, userId);
        int commentCount = jdbcTemplate.queryForObject("SELECT COUNt(user_id) FROM comments WHERE user_id = ?",
                Integer.class, userId);
        return new FindUserDto(writeCount, commentCount, 0);
    }

    public Long findUserId(String email) {
        User user = jdbcTemplate.queryForObject("SELECT id from users WHERE email = ?",
                (rs, rowNum) -> {
                    return User.builder()
                            .id(rs.getLong("id"))
                            .build();
                }, email);
        return user.getId();
    }

    public String findByUserName(Long id) {
        return jdbcTemplate.queryForObject("SELECT name FROM users WHERE id = ?", String.class, id);
    }
    public String findPassword(String email) {
        return jdbcTemplate.queryForObject("SELECT password FROM users WHERE email = ?", String.class, email);
    }
    public boolean exist(String email) {
        try {
            jdbcTemplate.queryForObject("SELECT id FROM users WHERE email = ?", Long.class, email);
        } catch(EmptyResultDataAccessException ex) {
            return true;
        }
        return false;
    }

}
