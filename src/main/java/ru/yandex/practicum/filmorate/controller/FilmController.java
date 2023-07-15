package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Добавляем Film: {}", film);
        return filmService.addFilm(film);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Film> findAll() {
        return filmService.getAllFilms();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film findOne(@PathVariable int id) {
        return filmService.getFilm(id);
    }

    @GetMapping(value = "/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<Film> findPopular(@RequestParam(value = "count", defaultValue = "10", required = false)
                                     Integer count) {
        return filmService.getRating(count);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film update(@Valid @RequestBody Film film) {
        log.debug("Обновляем Film: {}", film);
        return filmService.updateFilm(film);
    }

    @PutMapping(value = "/{filmId}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean likeFilm(@PathVariable int filmId, @PathVariable int userId) {
        log.debug("Добавляем рекомендацию User id={} для Film id={}", userId, filmId);
        return filmService.like(filmId, userId);
    }

    @DeleteMapping(value = "/{filmId}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean unlikeFilm(@PathVariable int filmId, @PathVariable int userId) {
        log.debug("Удаляем рекомендацию User id={} для Film id={}", userId, filmId);
        return filmService.unlike(filmId, userId);
    }
}
