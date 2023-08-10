package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void unlike(Integer filmId, Integer userId) {
        String sqlLike = "DELETE FROM LIKES WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlLike, filmId, userId);
    }

    @Override
    public void like(Integer filmId, Integer userId) {
        String sqlLike = "INSERT INTO LIKES (film_id, user_id)"
                + " VALUES(?,?)";
        jdbcTemplate.update(sqlLike, filmId, userId);
    }

    @Override
    public List<Film> getPopular(Integer end) {
        String sqlFilms = "SELECT * FROM LIKES " +
                "RIGHT JOIN FILMS ON LIKES.film_id = FILMS.film_id " +
                "JOIN MPA ON FILMS.mpa_id = MPA.mpa_id " +
                "GROUP BY FILMS.film_id " +
                "ORDER BY COUNT(user_id) DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(sqlFilms, this::buildFilm, end);
    }

    private Film buildFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("FILMS.film_id"))
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
