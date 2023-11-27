package com.example.matchteam.repository.board.comment;

import com.example.matchteam.domain.board.Board;
import com.example.matchteam.dto.board.BoardListDto;
import com.example.matchteam.dto.board.comment.EnrolCommentDto;
import com.example.matchteam.dto.board.comment.FindCommentDto;
import com.example.matchteam.repository.board.BoardRepository;
import com.example.matchteam.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Repository
@Slf4j
public class CommentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public CommentRepository(DataSource dataSource, UserRepository userRepository, BoardRepository boardRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    public Long enrol(EnrolCommentDto dto) {
        Long userId = userRepository.findUserId(dto.getEmail());
        Long boardId = boardRepository.findBoardId(dto.getBoardTitle(), dto.getBoardContent());
        try {
            jdbcTemplate.queryForObject(
                    "SELECT id FROM comments WHERE content = ? AND created_date = ? AND user_id = ? AND board_id = ?",
                    Long.class,
                    dto.getCommentContent(),
                    Timestamp.valueOf(LocalDateTime.now()),
                    userId,
                    boardId
            );
        } catch (EmptyResultDataAccessException ex) {

            jdbcTemplate.update(
                    conn -> {
                        PreparedStatement stmt = conn.prepareStatement("INSERT INTO comments(content, created_date, user_id, board_id) VALUES(?, ? ,?,?)");
                        stmt.setString(1, dto.getCommentContent());
                        stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                        stmt.setLong(3, userId);
                        stmt.setLong(4, boardId);
                        stmt.executeUpdate();
                        return stmt;
                    }
            );
        }
        return userId;
    }

    public FindCommentDto find(Long boardId) {
        Long userId = boardRepository.findUserId(boardId);
        String username = userRepository.findByUserName(userId);
        return jdbcTemplate.query("SELECT * from comments WHERE board_id = ?",
                (rs, rowNum) -> {
                    return FindCommentDto.builder()
                            .content(rs.getString("content"))
                            .createdDate(rs.getTimestamp("created_date"))
                            .name(username)
                            .build();
                }, boardId).stream().findFirst().get();
    }

    public boolean delete(String content, String email) {
        Long userId = userRepository.findUserId(email);
        int update = jdbcTemplate.update("DELETE FROM comments WHERE content = ? and user_id = ?", content, userId);
        if (update >= 1)
            return true;
        return false;
    }

    public List<BoardListDto> findAll(String email) {
        Long userId = userRepository.findUserId(email);

        List<Long> boardIdList = jdbcTemplate.queryForList("SELECT board_id FROM comments WHERE user_id = ?", Long.class, userId);

        List<BoardListDto> dtoList = new ArrayList<>();
        if (!boardIdList.isEmpty()) {
            // IN 절에 사용될 String 생성
            StringJoiner joiner = new StringJoiner(",", "(", ")");
            for (Long boardId : boardIdList) {
                joiner.add(boardId.toString());
            }

            // IN 절을 사용하여 boards 조회
            String sql = "SELECT * FROM boards WHERE id IN " + joiner.toString();
            List<Board> boards = jdbcTemplate.query(sql,
                    (rs, rowNum) -> Board.builder()
                            .id(rs.getLong("id"))
                            .title(rs.getString("title"))
                            .content(rs.getString("content"))
                            .viewCnt(rs.getInt("view_cnt"))
                            .createdDate(rs.getTimestamp("created_date"))
                            .statusId(rs.getInt("status_id"))
                            .build());

            // 나머지 코드는 동일하게 유지됩니다.
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
        }
        return dtoList;
    }

    public int getWriteCount(String email) {
        Long userId = userRepository.findUserId(email);
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM comments WHERE user_id = ?", Integer.class, userId);
    }

}
