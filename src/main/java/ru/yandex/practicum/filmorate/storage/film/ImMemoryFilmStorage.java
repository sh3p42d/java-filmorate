package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
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
        for (Film f : filmsMap.values()) {
            // Если проверять только на имя, то режиссерская версия Властелина колец
            // будет считаться тем же фильмом, что и обычная
            // В проверку включены все private final поля
            if (film.getName().equals(f.getName()) &&
                    film.getDuration() == f.getDuration() &&
                    film.getReleaseDate().equals(f.getReleaseDate()) &&
                    film.getDescription().equals(f.getDescription())) {
                throw new AlreadyExistsException("Такой Film уже есть. Его id=" + f.getId());
            }
        }

        film.setId(filmNextId);
        filmsMap.put(filmNextId, film);
        filmNextId++;
        return film;
    }

    @Override
    public Film getFilm(int id) {
        if (!filmsMap.containsKey(id)) {
            throw new NotPresentException("Film с id=" + id + " не найден");
        }
        return filmsMap.get(id);
    }

    @Override
    public Set<Film> getAllFilms() {
        return new HashSet<>(filmsMap.values());
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmsMap.containsKey(film.getId())) {
            filmsMap.replace(film.getId(), film);
            return film;
        } else {
            throw new NotPresentException("Film с id=" + film.getId() + " не найден");
        }
    }

    @Override
    public void removeFilm(Film film) {
        filmsMap.remove(film.getId());
    }
}
