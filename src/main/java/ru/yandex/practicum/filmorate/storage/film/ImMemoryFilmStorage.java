package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ImMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> filmsMap = new HashMap<>();
    private int filmNextId = 1;

    @Override
    public Film addFilm(Film film) {
        film.setId(filmNextId);
        filmsMap.put(filmNextId, film);
        filmNextId++;
        return film;
    }

    @Override
    public Film getFilm(int id) {
        return filmsMap.get(id);
    }

    @Override
    public Set<Film> getAllFilms() {
        return new HashSet<>(filmsMap.values());
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmsMap.containsKey(film.getId())) {
            filmsMap.put(film.getId(), film);
            return film;
        }
        return null;
    }

    @Override
    public boolean removeFilm(Film film) {
        if (filmsMap.containsKey(film.getId())) {
            filmsMap.remove(film.getId());
            return true;
        }
        return false;
    }
}
