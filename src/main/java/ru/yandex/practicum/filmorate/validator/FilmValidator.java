package ru.yandex.practicum.filmorate.validator;


import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FilmValidator implements ConstraintValidator<FilmValid, Film> {

    @Override
    public boolean isValid(Film film, ConstraintValidatorContext context) {
        return film.getReleaseDate().isAfter(LocalDate.parse("1895-12-28"));
    }
}
