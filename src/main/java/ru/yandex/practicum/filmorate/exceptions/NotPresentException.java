package ru.yandex.practicum.filmorate.exceptions;

public class NotPresentException extends RuntimeException {
    public NotPresentException(String message) {
        super(message);
    }
}
