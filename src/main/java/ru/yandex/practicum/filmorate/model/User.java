package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Integer id;

    @NotBlank(message = "Почта пользователя не может быть пустой")
    @Email(message = "Почта не соответствует формату mail@domain.com")
    private final String email;

    @NotBlank(message = "Логин пользователя не может быть пустым")
    @Pattern(regexp = "([\\x21-\\x7E]+)", message = "Логин пользователя не может содержать пробелы")
    private final String login;

    private String name;

    @PastOrPresent(message = "День рождения пользователя не может быть в будущем")
    private final LocalDate birthday;

    @JsonIgnore
    private Set<Integer> friends = new HashSet<>();

    public User(String email, String login, LocalDate birthday) {
        this.email = email;
        this.login = login;
        if (this.name == null) {
            this.name = login;
        }
        this.birthday = birthday;
    }
}
