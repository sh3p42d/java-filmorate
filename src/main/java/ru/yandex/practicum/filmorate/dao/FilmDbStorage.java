package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

@Component
@Qualifier("FilmDbStorage")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> getAllFilms() {
            String sqlQuery = "SELECT * FROM MPA, FILMS " +
                    "WHERE MPA.mpa_id = FILMS.mpa_id ";
            return jdbcTemplate.query(sqlQuery, this::buildFilm);
    }

    @Override
    public Film addFilm(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sqlQuery = "INSERT INTO FILMS (film_name, description, release_date, duration, mpa_id) " +
                "VALUES(?,?,?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery,  new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setLong(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());

            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (getFilm(film.getId()) == null) {
            throw new NotPresentException("Нет такого Film с id=" + film.getId());
        }

        String sqlQuery = "UPDATE FILMS " +
                "SET film_name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                "WHERE film_id = ?";

            jdbcTemplate.update(sqlQuery,
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getMpa().getId(),
                    film.getId());
        return film;
    }

    @Override
    public void removeFilm(Film film) {
        if (getFilm(film.getId()) == null) {
            throw new NotPresentException("Нет такого Film с id=" + film.getId());
        }

        String sqlQuery = "DELETE FROM FILMS WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    @Override
    public Film getFilm(int id) {
        String sqlQuery = "SELECT * FROM MPA, FILMS " +
                "WHERE FILMS.mpa_id = MPA.mpa_id AND film_id = ?";
        try {
            return jdbcTemplate.query(sqlQuery, this::buildFilm, id).iterator().next();
        } catch (RuntimeException e) {
            throw new NotPresentException("Нет такого Film с id=" + id);
        }
    }

    private Film buildFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("film_id"))
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(Mpa.builder()
                        .id(resultSet.getInt("mpa_id"))
                        .name(resultSet.getString("mpa_name")).build())
                .genres(new LinkedHashSet<>())
                .build();
    }
}
