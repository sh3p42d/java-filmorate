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
        String sqlQuery = "INSERT INTO FRIENDS (user_id, friend_id) VALUES(?,?)";
        jdbcTemplate.update(sqlQuery,userId, friendId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        String sqlQuery = "DELETE FROM FRIENDS WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getFriends(Integer id) {
        String sqlQuery = "SELECT * FROM USERS, FRIENDS " +
                "WHERE USERS.user_id = FRIENDS.friend_id AND FRIENDS.user_id = ?";

        return jdbcTemplate.query(sqlQuery, this::buildFriendUser, id);
    }

    @Override
    public List<User> getCommonFriends(int userId, int friendId) {
        String sqlQuery = " SELECT * FROM USERS " +
                " WHERE user_id in (SELECT friend_id FROM FRIENDS " +
                " WHERE friend_id in (SELECT friend_id FROM FRIENDS " +
                " WHERE user_id = ?) AND user_id = ?)";

        return jdbcTemplate.query(sqlQuery, this::buildFriendUser, friendId, userId);
    }

    private User buildFriendUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .name(resultSet.getString("username"))
                .build();
    }
}
