package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.FilmValid;
import javax.validation.constraints.*;
import java.time.LocalDate;

@FilmValid
@Data
public class Film {
    private int id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private final String name;

    @Size(max = 200, message = "Описание фильма не может превышать 200 символов")
    private final String description;

    private final LocalDate releaseDate;

    @Min(value = 1, message = "Продолжительность фильма не может быть отрицательной")
    private final int duration;
}
