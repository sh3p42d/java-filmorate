package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(int userId, int friendId) {
        String sqlQuery = "INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES(?,?)";
        jdbcTemplate.update(sqlQuery,userId, friendId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        String sqlQuery = "DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getFriends(Integer id) {
        String sqlQuery = "SELECT * FROM USERS, FRIENDS " +
                "WHERE USERS.USER_ID = FRIENDS.FRIEND_ID AND FRIENDS.USER_ID = ?";

        return jdbcTemplate.query(sqlQuery, this::getFriend, id);
    }

    @Override
    public List<User> getCommonFriends(int userId, int friendId) {
        String sqlQuery = " SELECT * FROM USERS " +
                " WHERE USER_ID in (SELECT FRIEND_ID FROM FRIENDS " +
                " WHERE friend_id in (SELECT friend_id FROM FRIENDS " +
                " WHERE USER_ID = ?) AND USER_ID = ?)";

        return jdbcTemplate.query(sqlQuery, this::getFriend, friendId, userId);
    }

    private User getFriend(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("USER_ID"))
                .email(resultSet.getString("EMAIL"))
                .login(resultSet.getString("LOGIN"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .name(resultSet.getString("USERNAME"))
                .build();
    }
}
