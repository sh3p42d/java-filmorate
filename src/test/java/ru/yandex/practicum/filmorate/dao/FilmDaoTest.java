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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDaoTest extends BuildUtil {
    private final FilmDbStorage filmDbStorage;

    @Test
    public void shouldCreateFilm() {
        Film testFilm = testFilmBuilder(4, "wsx", "edc",
                LocalDate.of(1954, 4, 4), 90, 4, "R");
        filmDbStorage.addFilm(testFilm);

        assertEquals(testFilm, filmDbStorage.getFilm(testFilm.getId()));
    }

    @Test
    public void shouldGetFilmById() {
        Film filmExpected = testFilmBuilder(1, "I love Linux", "Help me",
                LocalDate.of(1951, 1, 1), 90, 1, "G");
        Film filmActual = filmDbStorage.getFilm(1);

        assertEquals(filmExpected, filmActual);
    }

    @Test
    public void shouldGetAllFilms() {
        Film filmExpected1 = testFilmBuilder(1, "I love Linux", "Help me",
                LocalDate.of(1951, 1, 1), 90, 1, "G");
        Film filmExpected2 = testFilmBuilder(2, "Qwe", "asd",
                LocalDate.of(1952, 2, 2), 90, 2, "PG");
        Film filmExpected3 = testFilmBuilder(3, "zxc", "qaz",
                LocalDate.of(1953, 3, 3), 90, 3, "PG-13");

        List<Film> listExpected = new ArrayList<>();
        listExpected.add(filmExpected1);
        listExpected.add(filmExpected2);
        listExpected.add(filmExpected3);
        List<Film> listActual = filmDbStorage.getAllFilms();

        assertEquals(listExpected, listActual);
    }

    @Test
    public void shouldNotGetFilm() {
        assertThrows(NotPresentException.class, () -> filmDbStorage.getFilm(9999));
    }

    @Test
    public void shouldUpdateFilm() {
        Film testFilm = testFilmBuilder(4, "wsx", "edc",
                LocalDate.of(1954, 4, 4), 90, 4, "R");
        filmDbStorage.addFilm(testFilm);

        Film updatedTestFilm = testFilmBuilder(4, "qwerty", "zxc",
                LocalDate.of(1954, 4, 4), 90, 4, "R");

        filmDbStorage.updateFilm(updatedTestFilm);
        assertEquals(updatedTestFilm, filmDbStorage.getFilm(updatedTestFilm.getId()));
        assertEquals(updatedTestFilm.getName(), filmDbStorage.getFilm(updatedTestFilm.getId()).getName());
    }

    @Test
    public void shouldNotUpdateFilm() {
        Film testFilm = testFilmBuilder(9999, "wsx", "edc",
                LocalDate.of(1954, 4, 4), 90, 4, "R");

        assertThrows(NotPresentException.class, () -> filmDbStorage.updateFilm(testFilm));
    }

    @Test
    public void shouldDeleteFilm() {
        Film testFilm = testFilmBuilder(4, "wsx", "edc",
                LocalDate.of(1954, 4, 4), 90, 4, "R");
        filmDbStorage.addFilm(testFilm);
        assertEquals(testFilm, filmDbStorage.getFilm(testFilm.getId()));

        filmDbStorage.removeFilm(testFilm);
        assertThrows(NotPresentException.class, () -> filmDbStorage.getFilm(testFilm.getId()));
    }

    @Test
    public void shouldNotDeleteFilm() {
        Film testFilm = testFilmBuilder(4, "wsx", "edc",
                LocalDate.of(1954, 4, 4), 90, 4, "R");

        assertThrows(NotPresentException.class, () -> filmDbStorage.removeFilm(testFilm));
    }
}
