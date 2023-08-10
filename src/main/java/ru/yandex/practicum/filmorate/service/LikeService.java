package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeStorage likeStorage;

    public void unlike(Integer filmId, Integer userId) {
        likeStorage.unlike(filmId, userId);
    }

    public void like(Integer filmId, Integer userId) {
        likeStorage.like(filmId, userId);
    }

    public List<Film> getPopular(Integer end) {
        return likeStorage.getPopular(end);
    }
}
