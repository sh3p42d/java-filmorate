package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;

    @NotBlank(message = "Почта пользователя не может быть пустой")
    @Email(message = "Почта не соответствует формату mail@domain.com")
    private final String email;

    @NotBlank(message = "Логин пользователя не может быть пустым")
    @Pattern(regexp = "([\\x21-\\x7E]+)", message = "Логин пользователя не может содержать пробелы")
    private final String login;

    private String name;

    @PastOrPresent(message = "День рождения пользователя не может быть в будущем")
    private final LocalDate birthday;
}
