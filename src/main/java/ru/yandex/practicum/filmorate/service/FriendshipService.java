package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipStorage friendshipStorage;

    public void addFriend(int userId, int friendId) {
        friendshipStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) {
        friendshipStorage.deleteFriend(userId, friendId);
    }

    public List<User> getFriends(Integer id) {
        return friendshipStorage.getFriends(id);
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        return friendshipStorage.getCommonFriends(userId, friendId);
    }
}
