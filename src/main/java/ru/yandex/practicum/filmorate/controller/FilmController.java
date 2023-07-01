package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends RequestController<Film> {

    @Override
    protected boolean checkIsExist(Film film) {
        return mapOfInfo.containsKey(film.getId());
    }

    @Override
    protected int extractId(Film film) {
        return film.getId();
    }

    @Override
    protected void addId(Film film, int id) {
        film.setId(id);
    }
}
