package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;

public interface GenreStorage {
    List<Genre> getAllGenres();

    Genre getGenre(int id);

    void addGenre(Film film);

    void load(List<Film> films);

    void deleteGenre(int id);
}
