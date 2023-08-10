package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikeService likeService;
    private final GenreService genreService;

    public void like(int filmId, int userId) {
        if (userId <= 0) {
            throw new NotPresentException("Нет такого User с id=" + userId);
        }
        likeService.like(filmId, userId);
    }

    public void unlike(int filmId, int userId) {
        if (userId <= 0) {
            throw new NotPresentException("Нет такого User с id=" + userId);
        }
        likeService.unlike(filmId, userId);
    }

    public List<Film> getPopular(Integer count) {
        List<Film> filmList = likeService.getPopular(count);
        genreService.load(filmList);
        return filmList;
    }

    public Film addFilm(Film film) {
        filmStorage.addFilm(film);
        if (film.getGenres() != null) {
            genreService.addGenre(film);
        }

        return film;
    }

    public Film getFilm(int id) {
        Film film = filmStorage.getFilm(id);
        genreService.load(List.of(film));
        return film;
    }

    public List<Film> getAllFilms() {
        List<Film> filmList = filmStorage.getAllFilms();
        genreService.load(filmList);
        return filmList;
    }

    public Film updateFilm(Film film) {
        if (film.getGenres() != null) {
            genreService.deleteGenre(film.getId());
        }
        filmStorage.updateFilm(film);
        if (film.getGenres() != null) {
            genreService.addGenre(film);
        }
        return film;
    }

    public void removeFilm(Film film) {
        filmStorage.removeFilm(film);
    }
}
