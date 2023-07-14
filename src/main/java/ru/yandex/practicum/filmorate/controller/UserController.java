package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<User> findAll() {
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
        return userService.updateUser(user);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean addFriend(@Valid @PathVariable int id, @PathVariable int friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteFriend(@Valid @PathVariable int id, @PathVariable int friendId) {
        return userService.removeFriend(id, friendId);
    }
}
