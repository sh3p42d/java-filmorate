package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

public class FilmControllerTest extends ControllerTest<Film> {
    @BeforeEach
    public void beforeEach() {
        url = "/films";
        date = LocalDate.parse("2222-02-22");
        valueForTest = getValidValueForTest();
        valueForPostTest = getValueForPostTest();
    }

    @Override
    protected Film getValidValueForTest() {
        return Film.builder()
                .id(1)
                .name("film_name")
                .description("description")
                .releaseDate(date)
                .duration(120)
                .mpa(Mpa.builder()
                        .id(1)
                        .name(("G")).build())
                .genres(new LinkedHashSet<>())
                .build();
    }

    @Override
    protected Film getValueForPostTest() {
        return Film.builder()
                .id(4)
                .name("test_name")
                .description("test_description")
                .releaseDate(date)
                .duration(120)
                .mpa(Mpa.builder()
                        .id(2)
                        .name(("PG")).build())
                .genres(new LinkedHashSet<>())
                .build();
    }

    @Override
    protected void setValueForTestId(int id) {
        valueForTest.setId(id);
    }

    @Override
    protected void setValueForPostTest(int id) {
        valueForPostTest.setId(id);
    }

    @Override
    protected String valueForTestToString(Film film) {
        return "{\"id\":" + film.getId() +
                ",\"name\":\"" + film.getName() +
                "\",\"description\":\"" + film.getDescription() +
                "\",\"releaseDate\":\"" + film.getReleaseDate() +
                "\",\"duration\":" + film.getDuration() +
                ",\"genres\":" + film.getGenres() +
                ",\"mpa\":" + "{" +
                    "\"id\":" + film.getMpa().getId() +
                    ",\"name\":\"" + film.getMpa().getName() + "\"" + "}" +
                "}";
    }

    protected static Stream<Arguments> invalidFields() {
        return Stream.of(
                Arguments.of("name", " "),
                Arguments.of("name", null),
                Arguments.of("description",
                        "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"),
                Arguments.of("releaseDate", LocalDate.parse("1800-01-01")),
                Arguments.of("duration", -100)
        );
    }
}

