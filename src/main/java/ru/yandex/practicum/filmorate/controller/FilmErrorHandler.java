package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@RestControllerAdvice(assignableTypes = FilmController.class)
public class FilmErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmNotFoundException(final NotPresentException e) {
        return new ErrorResponse("error", e.getMessage());
    }
}
