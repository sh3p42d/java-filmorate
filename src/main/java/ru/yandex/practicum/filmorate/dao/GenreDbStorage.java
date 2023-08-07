package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAllGenres() {
        String sqlQuery = "SELECT * FROM GENRE ";
        return jdbcTemplate.query(sqlQuery, this::findGenre);
    }

    @Override
    public Genre getGenre(int id) {
        String sqlQuery = "SELECT * FROM GENRE WHERE GENRE_ID = ?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::findGenre, id);
        } catch (RuntimeException e) {
            throw new NotPresentException("Нет такого Genre с id=" + id);
        }
    }

    public void deleteGenre(int id) {
        String sqlQuery = "DELETE FROM GENRE_FILM WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    public void addGenre(Film film) {
        String sqlQuery = "INSERT INTO GENRE_FILM (FILM_ID, GENRE_ID) VALUES(?,?)";

        List<Integer> genres = film.getGenres()
                .stream()
                .map(Genre::getId)
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, film.getId());
                ps.setInt(2, genres.get(i));
            }

            @Override
            public int getBatchSize() {
                return genres.size();
            }
        });
    }

    private Genre findGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("GENRE_ID"))
                .name(resultSet.getString("GENRE_NAME"))
                .build();

    }

    @Override
    public void load(List<Film> films) {
        final Map<Integer, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, identity()));

        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));

        final String sqlQuery = "SELECT * FROM GENRE g, " +
                "GENRE_FILM gf WHERE gf.GENRE_ID = g.GENRE_ID AND gf.FILM_ID IN (" + inSql + ")";

        jdbcTemplate.query(sqlQuery, (rs) -> {
            final Film film = filmById.get(rs.getInt("FILM_ID"));
            film.addGenre(findGenre(rs, 0));
        }, films.stream().map(Film::getId).toArray());
    }
}
