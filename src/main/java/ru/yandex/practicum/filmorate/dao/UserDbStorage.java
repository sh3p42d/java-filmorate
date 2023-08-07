package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Qualifier("UserDbStorage")
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllUsers() {
            String sqlQuery = "SELECT * FROM USERS";
            return jdbcTemplate.query(sqlQuery, this::findUserById);
    }

    @Override
    public User addUser(User user) {
        String sqlQuery = "INSERT INTO USERS ( EMAIL, LOGIN, BIRTHDAY, USERNAME) " +
                "VALUES(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setDate(3, Date.valueOf(user.getBirthday()));
            stmt.setString(4, user.getName());

            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (getUser(user.getId()) == null)  {
            throw new NotPresentException("Нет такого User с id=" + user.getId());
        }
        String sqlQuery = "UPDATE USERS SET " +
                "USERNAME = ?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ?  " +
                "WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public void removeUser(User user) {
        if (getUser(user.getId()) == null)  {
            throw new NotPresentException("Нет такого User с id=" + user.getId());
        }

        String sqlQuery = "DELETE FROM USERS WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery, user.getId());
    }

    @Override
    public User getUser(int id) {
        String sqlQuery = "SELECT USER_ID, EMAIL, LOGIN, BIRTHDAY, USERNAME FROM USERS " +
                "WHERE USER_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::findUserById, id);
        } catch (RuntimeException e) {
            throw new NotPresentException("Нет такого User с id=" + id);
        }
    }

    private User findUserById(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("USER_ID"))
                .email(resultSet.getString("EMAIL"))
                .login(resultSet.getString("LOGIN"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .name(resultSet.getString("USERNAME"))
                .build();
    }
}
