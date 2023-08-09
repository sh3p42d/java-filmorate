package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.helper.BuildUtil;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDaoTest extends BuildUtil {
    private final GenreDbStorage genreDbStorage;
    private final FilmDbStorage filmDbStorage;

    @Test
    public void shouldGetGenreById() {
        Genre testGenre = testGenreBuilder(1, "Комедия");

        assertEquals(testGenre, genreDbStorage.getGenre(testGenre.getId()));
    }

    @Test
    public void shouldNotGetGenreById() {
        assertThrows(NotPresentException.class, () -> genreDbStorage.getGenre(9999));
    }

    @Test
    public void shouldGetAllGenres() {
        Genre testGenre1 = testGenreBuilder(1, "Комедия");
        Genre testGenre2 = testGenreBuilder(2, "Драма");
        Genre testGenre3 = testGenreBuilder(3, "Мультфильм");
        Genre testGenre4 = testGenreBuilder(4, "Триллер");
        Genre testGenre5 = testGenreBuilder(5, "Документальный");
        Genre testGenre6 = testGenreBuilder(6, "Боевик");

        List<Genre> listGenres = new ArrayList<>();
        listGenres.add(testGenre1);
        listGenres.add(testGenre2);
        listGenres.add(testGenre3);
        listGenres.add(testGenre4);
        listGenres.add(testGenre5);
        listGenres.add(testGenre6);

        assertEquals(listGenres, genreDbStorage.getAllGenres());
    }

    @Test
    public void shouldAddGenreToFilm() {
        Film film = testFilmBuilder(1, "I love Linux", "Help me",
                LocalDate.of(1951, 1, 1), 90, 1, "G");
        Genre testGenre = testGenreBuilder(1, "Комедия");
        film.addGenre(testGenre);
        filmDbStorage.updateFilm(film);
        genreDbStorage.addGenre(film);
        LinkedHashSet<Genre> actualGenres = new LinkedHashSet<>();
        actualGenres.add(testGenre);

        assertEquals(film.getGenres(), actualGenres);
    }

    @Test
    public void shouldDeleteGenre() {
        Film film = testFilmBuilder(1, "I love Linux", "Help me",
                LocalDate.of(1951, 1, 1), 90, 1, "G");
        Genre testGenre = testGenreBuilder(1, "Комедия");
        film.addGenre(testGenre);
        filmDbStorage.updateFilm(film);
        genreDbStorage.addGenre(film);
        LinkedHashSet<Genre> actualGenres = new LinkedHashSet<>();
        actualGenres.add(testGenre);
        assertEquals(film.getGenres(), actualGenres);

        genreDbStorage.deleteGenre(1);
        LinkedHashSet<Genre> emptyGenres = new LinkedHashSet<>();
        film.setGenres(emptyGenres);
        filmDbStorage.updateFilm(film);
        assertEquals(emptyGenres, film.getGenres());
    }
}
