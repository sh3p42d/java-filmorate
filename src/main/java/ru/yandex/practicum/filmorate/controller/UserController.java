package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) {
        log.debug("Добавляем User: {}", user);
        return userService.addUser(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findOne(@PathVariable int id) {
        return userService.getUser(id);
    }

    @GetMapping(value = "/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findFriends(@Valid @PathVariable int id) {
        return userService.getFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findCommon(@PathVariable int id, @PathVariable int otherId) {
        return userService.commonFriends(id, otherId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User update(@Valid @RequestBody User user) {
        log.debug("Обновляем User: {}", user);
        return userService.updateUser(user);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@Valid @PathVariable int id, @PathVariable int friendId) {
        log.debug("Добавляем для User id={} друга User id={}", id, friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@Valid @PathVariable int id, @PathVariable int friendId) {
        log.debug("Удаляем у User id={} друга User id={}", id, friendId);
        userService.removeFriend(id, friendId);
    }
}
