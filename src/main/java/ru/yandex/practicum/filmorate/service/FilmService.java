package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService implements FilmStorage {
    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public boolean like(int filmId, int userId) {
        Film film = filmStorage.getFilm(filmId);

        if (Objects.equals(film, null)) {
            throw new NotPresentException("Нет Film с id=" + filmId);
        }

        return filmStorage.getFilm(filmId).getLikes().add(userId);
    }

    public boolean unlike(int filmId, int userId) {
        Film film = filmStorage.getFilm(filmId);

        if (Objects.equals(film, null)) {
            throw new NotPresentException("Нет Film с id=" + filmId);
        }
        if (!film.getLikes().contains(userId)) {
            throw new NotPresentException("Нет User с id=" + userId);
        }

        return filmStorage.getFilm(filmId).getLikes().remove(userId);
    }

    public List<Film> getRating(Integer count) {
        return filmStorage.getAllFilms().stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(film -> film.getLikes().size())))
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    @Override
    public Film getFilm(int id) {
        if (Objects.equals(filmStorage.getFilm(id), null)) {
            throw new NotPresentException("Нет Film с id=" + id);
        }
        return filmStorage.getFilm(id);
    }

    @Override
    public Set<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @Override
    public Film updateFilm(Film film) {
        int id = film.getId();
        if (Objects.equals(filmStorage.getFilm(id), null)) {
            throw new NotPresentException("Нет Film с id=" + id);
        }
        return filmStorage.updateFilm(film);
    }

    @Override
    public boolean removeFilm(Film film) {
        return filmStorage.removeFilm(film);
    }
}
