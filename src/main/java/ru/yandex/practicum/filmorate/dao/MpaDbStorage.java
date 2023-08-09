package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAllMpa() {
        String sqlQuery = "SELECT * FROM MPA ";
        return jdbcTemplate.query(sqlQuery,this::buildMpa);
    }

    @Override
    public Mpa getMpaById(Integer id) {
        String sqlQuery = "SELECT * FROM MPA WHERE mpa_id = ?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::buildMpa, id);
        } catch (RuntimeException e) {
            throw new NotPresentException("Нет такого MPA с id=" + id);
        }
    }

    private Mpa buildMpa(ResultSet resultSet, int i) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getInt("mpa_id"))
                .name(resultSet.getString("mpa_name"))
                .build();
    }
}
