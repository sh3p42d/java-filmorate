package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ImMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> usersMap = new HashMap<>();
    private int userNextId = 1;

    @Override
    public User addUser(User user) {
        for (User u : usersMap.values()) {
            // В проверку включены все private final поля
            if (user.getEmail().equals(u.getEmail()) &&
                    user.getLogin().equals(u.getLogin()) &&
                    user.getBirthday().equals(u.getBirthday())) {
                throw new AlreadyExistsException("Такой User уже существует. Его id=" + u.getId());
            }
        }

        user.setId(userNextId);
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        usersMap.put(userNextId, user);
        userNextId++;
        return user;
    }

    @Override
    public User getUser(int id) {
        if (!usersMap.containsKey(id)) {
            throw new NotPresentException("User с id=" + id + " не найден");
        }
        return usersMap.get(id);
    }

    @Override
    public Set<User> getAllUsers() {
        return new HashSet<>(usersMap.values());
    }

    @Override
    public User updateUser(User user) {
        if (usersMap.containsKey(user.getId())) {
            usersMap.replace(user.getId(), user);
            return user;
        } else {
            throw new NotPresentException("User с id=" + user.getId() + " не найден");
        }
    }

    @Override
    public void removeUser(User user) {
        usersMap.remove(user.getId());
    }
}
