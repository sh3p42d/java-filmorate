package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeStorage {
    void unlike(Integer filmId, Integer userId);

    void like(Integer filmId, Integer userId);

    List<Film> getPopular(Integer end);


}
