package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.helper.BuildUtil;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FriendshipDaoTest extends BuildUtil {
    private final FriendshipDbStorage friendshipDbStorage;

    @Test
    public void shouldAddAndGetFriend() {
        User userExpected1 = testUserBuilder(1, "test1@mail.com", "test_login1",
                "user_name1", LocalDate.of(2021, 1, 1));
        User userExpected2 = testUserBuilder(2, "test2@mail.com", "test_login2",
                "user_name2", LocalDate.of(2022, 2, 2));

        friendshipDbStorage.addFriend(userExpected1.getId(), userExpected2.getId());
        assertEquals(List.of(userExpected2), friendshipDbStorage.getFriends(userExpected1.getId()));
        assertEquals(Collections.EMPTY_LIST, friendshipDbStorage.getFriends(userExpected2.getId()));
    }

    @Test
    public void shouldDeleteFriend() {
        User userExpected1 = testUserBuilder(1, "test1@mail.com", "test_login1",
                "user_name1", LocalDate.of(2021, 1, 1));
        User userExpected2 = testUserBuilder(2, "test2@mail.com", "test_login2",
                "user_name2", LocalDate.of(2022, 2, 2));

        friendshipDbStorage.addFriend(userExpected1.getId(), userExpected2.getId());
        assertEquals(List.of(userExpected2), friendshipDbStorage.getFriends(userExpected1.getId()));
        assertEquals(Collections.EMPTY_LIST, friendshipDbStorage.getFriends(userExpected2.getId()));

        friendshipDbStorage.deleteFriend(userExpected1.getId(), userExpected2.getId());
        assertEquals(Collections.EMPTY_LIST, friendshipDbStorage.getFriends(userExpected1.getId()));
        assertEquals(Collections.EMPTY_LIST, friendshipDbStorage.getFriends(userExpected2.getId()));
    }

    @Test
    public void shouldGetCommonFriends() {
        User userExpected1 = testUserBuilder(1, "test1@mail.com", "test_login1",
                "user_name1", LocalDate.of(2021, 1, 1));
        User userExpected2 = testUserBuilder(2, "test2@mail.com", "test_login2",
                "user_name2", LocalDate.of(2022, 2, 2));
        User userExpected3 = testUserBuilder(3, "test3@mail.com", "test_login3",
                "user_name3", LocalDate.of(2023, 3, 3));

        friendshipDbStorage.addFriend(userExpected1.getId(), userExpected3.getId());
        friendshipDbStorage.addFriend(userExpected2.getId(), userExpected3.getId());
        assertEquals(List.of(userExpected3),
                friendshipDbStorage.getCommonFriends(userExpected1.getId(), userExpected2.getId()));
    }
}
