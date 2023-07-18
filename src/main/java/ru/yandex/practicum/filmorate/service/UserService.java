package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public boolean addFriend(int userId, int friendId) {
        userStorage.getUser(userId).getFriends().add(friendId);
        userStorage.getUser(friendId).getFriends().add(userId);
        return true;
    }

    public List<User> getFriends(int userId) {
        User user = userStorage.getUser(userId);

        List<Integer> friends = new ArrayList<>(user.getFriends());
        return userStorage.getAllUsers()
                .stream()
                .filter(u -> friends.contains(u.getId()))
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }

    public boolean removeFriend(int userId, int friendId) {
        userStorage.getUser(userId).getFriends().remove(friendId);
        userStorage.getUser(friendId).getFriends().remove(userId);
        return true;
    }

    public List<User> commonFriends(int userId, int friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);

        List<Integer> usersFriends = new ArrayList<>(user.getFriends());
        List<Integer> commonFriends = new ArrayList<>(friend.getFriends());

        return userStorage.getAllUsers().stream()
                .filter(u -> usersFriends.contains(u.getId()) && commonFriends.contains(u.getId()))
                .collect(Collectors.toList());
    }

    public User addUser(@Valid User user) {
        return userStorage.addUser(user);
    }

    public User getUser(int id) {
        return userStorage.getUser(id);
    }

    public Set<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void removeUser(User user) {
        userStorage.removeUser(user);
    }
}