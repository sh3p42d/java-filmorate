package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    protected int id = 1;
    protected final Map<Integer, User> mapOfInfo = new HashMap<>();

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(mapOfInfo.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        try {
            addId(user, id);
            mapOfInfo.put(id, user);
            log.info("Добавлен {}: {}", user.getClass(), user);
            id++;
        } catch (ValidationException e) {
            log.error("Ошибка в теле запроса");
        }
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        try {
            if (checkIsExist(user)) {
                mapOfInfo.put(extractId(user), user);
                log.info("Обновлен {}: {}", user.getClass(), user);
            } else {
                log.info("{} для обновления не найден", user);
                throw new NotPresentException(HttpStatus.NOT_FOUND, "Нет такого " + user);
            }
        } catch (ValidationException e) {
            log.error("Ошибка в теле запроса");
        }
        return user;
    }

    protected boolean checkIsExist(User user) {
        return mapOfInfo.containsKey(user.getId());
    }

    protected int extractId(User user) {
        return user.getId();
    }

    protected void addId(User user, int id) {
        user.setId(id);
    }
}
