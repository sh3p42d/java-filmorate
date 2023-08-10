package ru.yandex.practicum.filmorate.helper;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.LinkedHashSet;

public class BuildUtil {
    protected User testUserBuilder(Integer id, String email, String login, String name, LocalDate birthday) {
        return User.builder()
                .id(id)
                .email(email)
                .login(login)
                .name(name)
                .birthday(birthday)
                .build();
    }

    protected Film testFilmBuilder(Integer id, String name, String description, LocalDate releaseDate,
                                   Integer duration, Integer mpaId, String mpaName) {
        return Film.builder()
                .id(id)
                .name(name)
                .description(description)
                .releaseDate(releaseDate)
                .duration(duration)
                .mpa(Mpa.builder()
                        .id(mpaId)
                        .name(mpaName).build())
                .genres(new LinkedHashSet<>())
                .build();
    }

    protected Genre testGenreBuilder(Integer id, String name) {
        return Genre.builder()
                .id(id)
                .name(name)
                .build();
    }

    protected Mpa testMpaBuilder(Integer id, String name) {
        return Mpa.builder()
                .id(id)
                .name(name)
                .build();
    }
}
