package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class NotPresentException extends ResponseStatusException {
    public NotPresentException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
