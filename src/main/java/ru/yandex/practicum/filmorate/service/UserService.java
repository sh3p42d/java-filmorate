package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import javax.validation.Valid;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserStorage {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public boolean addFriend(int userId, int friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);

        if (Objects.equals(user, null) || Objects.equals(friend, null)) {
            throw new NotPresentException("Нет User с id=" + userId + " или User с id=" + friendId);
        }

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        return true;
    }

    public List<User> getFriends(int userId) {
        User user = userStorage.getUser(userId);

        if (Objects.equals(user, null)) {
            throw new NotPresentException("Нет User с id=" + userId);
        }

        List<Integer> friends = new ArrayList<>(user.getFriends());
        return userStorage.getAllUsers()
                .stream()
                .filter(u -> friends.contains(u.getId()))
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }

    public boolean removeFriend(int userId, int friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);

        if (Objects.equals(user, null) || Objects.equals(friend, null)) {
            throw new NotPresentException("Нет User с id=" + userId + " или User с id=" + friendId);
        }

        userStorage.getUser(userId).getFriends().remove(friendId);
        userStorage.getUser(friendId).getFriends().remove(userId);
        return true;
    }

    public List<User> commonFriends(int userId, int friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);

        if (Objects.equals(user, null) || Objects.equals(friend, null)) {
            throw new NotPresentException("Нет User с id=" + userId + " или User с id=" + friendId);
        }

        List<Integer> usersFriends = new ArrayList<>(user.getFriends());
        List<Integer> commonFriends = new ArrayList<>(friend.getFriends());

        return userStorage.getAllUsers().stream()
                .filter(u -> usersFriends.contains(u.getId()) && commonFriends.contains(u.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public User addUser(@Valid User user) {
        return userStorage.addUser(user);
    }

    @Override
    public User getUser(int id) {
        if (Objects.equals(userStorage.getUser(id), null)) {
            throw new NotPresentException("Нет User с id=" + id);
        }
        return userStorage.getUser(id);
    }

    @Override
    public Set<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @Override
    public User updateUser(User user) {
        int id = user.getId();
        if (Objects.equals(userStorage.getUser(id), null)) {
            throw new NotPresentException("Нет User с id=" + id);
        }
        return userStorage.updateUser(user);
    }

    @Override
    public boolean removeUser(User user) {
        return userStorage.removeUser(user);
    }
}