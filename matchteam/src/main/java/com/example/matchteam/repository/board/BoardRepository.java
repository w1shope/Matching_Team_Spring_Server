package com.example.matchteam.repository.board;

import com.example.matchteam.domain.board.Board;
import com.example.matchteam.domain.user.User;
import com.example.matchteam.dto.board.*;
import com.example.matchteam.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class BoardRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public BoardRepository(DataSource dataSource, UserRepository userRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userRepository = userRepository;
    }

    public List<BoardListDto> boards() {
        List<Board> boards = jdbcTemplate.query("SELECT * FROM boards",
                (rs, rowNum) -> {
                    return Board.builder()
                            .id(rs.getLong("id"))
                            .title(rs.getString("title"))
                            .content(rs.getString("content"))
                            .viewCnt(rs.getInt("view_cnt"))
                            .createdDate(rs.getTimestamp("created_date"))
                            .statusId(rs.getInt("status_id"))
                            .build();
                });
        List<BoardListDto> dtoList = new ArrayList<>();
        for (Board board : boards) {
            int status = jdbcTemplate.queryForObject("SELECT status FROM project_status WHERE id = ?", Integer.class, board.getStatusId());
            dtoList.add(new BoardListDto(
                    board.getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getViewCnt(),
                    board.getCreatedDate(),
                    status
            ));
        }
        return dtoList;
    }

    public List<BoardListDto> userBoards(String email) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?",
                (rs, rowNum) -> {
                    return User.builder()
                            .id(rs.getLong("id"))
                            .email(rs.getString("email"))
                            .name(rs.getString("name"))
                            .studentNum(rs.getInt("student_num"))
                            .department(rs.getString("department"))
                            .password(rs.getString("password"))
                            .development(rs.getString("development"))
                            .build();
                }, email);
        List<Board> boards = jdbcTemplate.query("SELECT * FROM boards WHERE user_id = ?",
                (rs, rowNum) -> {
                    return Board.builder()
                            .id(rs.getLong("id"))
                            .title(rs.getString("title"))
                            .content(rs.getString("content"))
                            .viewCnt(rs.getInt("view_cnt"))
                            .createdDate(rs.getTimestamp("created_date"))
                            .statusId(rs.getInt("status_id"))
                            .build();
                }, user.getId());
        List<BoardListDto> dtoList = new ArrayList<>();
        for (Board board : boards) {
            int status = jdbcTemplate.queryForObject("SELECT status FROM project_status WHERE id = ?", Integer.class, board.getStatusId());
            dtoList.add(new BoardListDto(
                    board.getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getViewCnt(),
                    board.getCreatedDate(),
                    status
            ));
        }
        return dtoList;
    }

    public Board findBoard(FindBoardDto dto) {
        return jdbcTemplate.queryForObject("SELECT * FROM boards WHERE title = ? and content = ? and created_date = ? and view_cnt = ?",
                (rs, rowNum) -> {
                    return Board.builder()
                            .id(rs.getLong("id"))
                            .title(rs.getString("title"))
                            .content(rs.getString("content"))
                            .viewCnt(rs.getInt("view_cnt"))
                            .createdDate(rs.getTimestamp("created_date"))
                            .build();
                }, dto.getTitle(), dto.getContent(), dto.getCreatedDate(), dto.getViewCnt());
    }


    public boolean enrol(EnrolBoardDto dto) {
        User loginUser = userRepository.findByEmail(dto.getEmail());
        long statusId = getStatusId();
        int update = jdbcTemplate.update(
                "INSERT INTO boards(title, content, view_cnt, created_date, user_id, status_id) VALUES(?, ?, ?, ?, ?, ?)",
                dto.getTitle(), dto.getContent(), 0, Timestamp.valueOf(LocalDateTime.now()), loginUser.getId(), statusId
        );
        if (update == 1)
            return true;
        return false;
    }

    private long getStatusId() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                conn -> {
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO project_status(status) VALUES(?)",
                            Statement.RETURN_GENERATED_KEYS);
                    stmt.setInt(1, 1);
                    return stmt;
                }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public boolean delete(DeleteBoardDto dto) {
        Long boardId = findBoardId(dto.getTitle(), dto.getContent());

        jdbcTemplate.update("DELETE FROM comments WHERE board_id = ?", boardId);

        Long userId = userRepository.findUserId(dto.getEmail());
        int update = jdbcTemplate.update("DELETE FROM boards WHERE title = ? and content = ? and user_id = ?",
                dto.getTitle(), dto.getContent(), userId);

        return update == 1;
    }

    public boolean update(UpdateBoardDto dto) {
        int update = jdbcTemplate.update("UPDATE boards SET title = ?, content = ? WHERE title = ? and content = ?",
                dto.getNewTitle(), dto.getNewContent(), dto.getOldTitle(), dto.getOldContent());
        return update == 1 ? true : false;
    }

    public Long findUserId(String title, String content) {
        return jdbcTemplate.queryForObject("SELECT user_id FROM boards WHERE title = ? and content = ?",
                (rs, rowNum) -> {
                    return User.builder()
                            .id(rs.getLong("user_id"))
                            .build();
                }, title, content).getId();
    }

    public boolean updateBoardStatus(BoardStatusDto dto, int status) {
        Long userId = findUserId(dto.getTitle(), dto.getContent());
        Long statsId = jdbcTemplate.queryForObject("SELECT status_id FROM boards WHERE title = ? and content = ? and user_id = ?", Long.class, dto.getTitle(), dto.getContent(), userId);
        int update = jdbcTemplate.update("UPDATE project_status SET status = ? WHERE id = ?", status, statsId);
        if (update == 1)
            return true;
        return false;
    }

    public Long findUserId(Long boardId) {
        return jdbcTemplate.queryForObject("SELECT user_id FROM boards WHERE id = ?", Long.class, boardId);
    }

    public Long findBoardId(String title, String content) {
        return jdbcTemplate.queryForObject("SELECT id FROM boards WHERE title = ? and content = ?", Long.class, title, content);
    }

    public List<BoardListDto> findById(Long userId) {
        List<Board> boards = jdbcTemplate.query("SELECT * FROM boards where user_id = ?",
                (rs, rowNum) -> {
                    return Board.builder()
                            .id(rs.getLong("id"))
                            .title(rs.getString("title"))
                            .content(rs.getString("content"))
                            .viewCnt(rs.getInt("view_cnt"))
                            .createdDate(rs.getTimestamp("created_date"))
                            .statusId(rs.getInt("status_id"))
                            .build();
                }, userId);
        List<BoardListDto> dtoList = new ArrayList<>();
        for (Board board : boards) {
            int status = jdbcTemplate.queryForObject("SELECT status FROM project_status WHERE id = ?", Integer.class, board.getStatusId());
            dtoList.add(new BoardListDto(
                    board.getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getViewCnt(),
                    board.getCreatedDate(),
                    status
            ));
        }
        return dtoList;
    }

    public int getWriteCount(String email) {
        Long userId = userRepository.findUserId(email);
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM boards WHERE user_id = ?", Integer.class, userId);
    }

}
