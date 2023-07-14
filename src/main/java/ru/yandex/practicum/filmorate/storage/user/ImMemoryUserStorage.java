package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
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
        return usersMap.get(id);
    }

    @Override
    public Set<User> getAllUsers() {
        return new HashSet<>(usersMap.values());
    }

    @Override
    public User updateUser(User user) {
        if (usersMap.containsKey(user.getId())) {
            usersMap.put(user.getId(), user);
            return user;
        }
        return null;
    }

    @Override
    public boolean removeUser(User user) {
        if (usersMap.containsKey(user.getId())) {
            usersMap.remove(user.getId());
            return true;
        }
        return false;
    }
}
