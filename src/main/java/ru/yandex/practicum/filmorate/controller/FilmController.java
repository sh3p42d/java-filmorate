package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    protected int id = 1;
    protected final Map<Integer, Film> mapOfInfo = new HashMap<>();

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(mapOfInfo.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        try {
            addId(film, id);
            mapOfInfo.put(id, film);
            log.info("Добавлен {}: {}", film.getClass(), film);
            id++;
        } catch (ValidationException e) {
            log.error("Ошибка в теле запроса");
        }
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        try {
            if (checkIsExist(film)) {
                mapOfInfo.put(extractId(film), film);
                log.info("Обновлен {}: {}", film.getClass(), film);
            } else {
                log.info("{} для обновления не найден", film);
                throw new NotPresentException(HttpStatus.NOT_FOUND, "Нет такого " + film);
            }
        } catch (ValidationException e) {
            log.error("Ошибка в теле запроса");
        }
        return film;
    }

    protected boolean checkIsExist(Film film) {
        return mapOfInfo.containsKey(film.getId());
    }

    protected int extractId(Film film) {
        return film.getId();
    }

    protected void addId(Film film, int id) {
        film.setId(id);
    }
}
