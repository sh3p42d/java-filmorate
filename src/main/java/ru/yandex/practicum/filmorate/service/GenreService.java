package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

    public Genre getGenre(int id) {
        return genreStorage.getGenre(id);
    }

    public void addGenre(Film film) {
        genreStorage.addGenre(film);
    }

    public void deleteGenre(int id) {
        genreStorage.deleteGenre(id);
    }

    public void load(List<Film> films) {
        genreStorage.load(films);
    }
}
