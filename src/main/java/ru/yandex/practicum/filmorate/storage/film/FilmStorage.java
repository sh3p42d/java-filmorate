package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Set;

public interface FilmStorage {
    Film addFilm(Film film);

    Film getFilm(int id);

    Set<Film> getAllFilms();

    Film updateFilm(Film film);

    boolean removeFilm(Film film);
}
