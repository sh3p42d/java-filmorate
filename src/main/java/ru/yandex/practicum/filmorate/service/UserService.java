package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userDbStorage;
    private final FriendshipService friendshipService;

    public void addFriend(int userId, int friendId) {
        if (userDbStorage.getUser(userId) == null || userDbStorage.getUser(friendId) == null) {
            throw new NotPresentException("Нет User с id=" + userId + " или его друга с id=" + friendId);
        }
        friendshipService.addFriend(userId, friendId);
    }

    public List<User> getFriends(int userId) {
        if (userDbStorage.getUser(userId) == null) {
            throw new NotPresentException("Нет User с id=" + userId);
        }
        return friendshipService.getFriends(userId);
    }

    public void removeFriend(int userId, int friendId) {
        friendshipService.deleteFriend(userId, friendId);
    }

    public List<User> commonFriends(int userId, int friendId) {
        return friendshipService.getCommonFriends(userId, friendId);
    }

    public User addUser(@Valid User user) {
        checkName(user);
        return userDbStorage.addUser(user);
    }

    public User getUser(int id) {
        return userDbStorage.getUser(id);
    }

    public List<User> getAllUsers() {
        return userDbStorage.getAllUsers();
    }

    public User updateUser(User user) {
        return userDbStorage.updateUser(user);
    }

    public void removeUser(User user) {
        userDbStorage.removeUser(user);
    }

    private void checkName(User user) {
        if (user.getName().isBlank() || user.getName() == null) {
            user.setName(user.getLogin());
        }
    }
}