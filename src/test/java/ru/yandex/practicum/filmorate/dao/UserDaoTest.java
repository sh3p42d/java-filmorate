package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;
import ru.yandex.practicum.filmorate.helper.BuildUtil;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDaoTest extends BuildUtil {
    private final UserDbStorage userDbStorage;

    @Test
    public void shouldCreateUser() {
        User testUser = testUserBuilder(4, "test4@mail.com", "test_login4",
                "user_name4", LocalDate.of(2020, 4, 4));
        userDbStorage.addUser(testUser);

        assertEquals(testUser, userDbStorage.getUser(testUser.getId()));
    }

    @Test
    public void shouldGetUserById() {
        User userExpected = testUserBuilder(1, "test1@mail.com", "test_login1",
                "user_name1", LocalDate.of(2021, 1, 1));
        User userActual = userDbStorage.getUser(1);

        assertEquals(userExpected, userActual);
    }

    @Test
    public void shouldNotGetUserByWrongId() {
        assertThrows(NotPresentException.class, () -> userDbStorage.getUser(9999));
    }

    @Test
    public void shouldGetAllUsers() {
        User userExpected1 = testUserBuilder(1, "test1@mail.com", "test_login1",
                "user_name1", LocalDate.of(2021, 1, 1));
        User userExpected2 = testUserBuilder(2, "test2@mail.com", "test_login2",
                "user_name2", LocalDate.of(2022, 2, 2));
        User userExpected3 = testUserBuilder(3, "test3@mail.com", "test_login3",
                "user_name3", LocalDate.of(2023, 3, 3));

        List<User> listExpected = new ArrayList<>();
        listExpected.add(userExpected1);
        listExpected.add(userExpected2);
        listExpected.add(userExpected3);
        List<User> listActual = userDbStorage.getAllUsers();

        assertEquals(listExpected, listActual);
    }

    @Test
    public void shouldUpdateUser() {
        User testUser = testUserBuilder(4, "test4@mail.com", "test_login4",
                "user_name4", LocalDate.of(2020, 4, 4));
        userDbStorage.addUser(testUser);
        User updatedTestUser = testUserBuilder(4, "test444444@mail.com", "test_login4",
                "user_name44444", LocalDate.of(2020, 4, 4));
        userDbStorage.updateUser(updatedTestUser);

        assertEquals(updatedTestUser, userDbStorage.getUser(updatedTestUser.getId()));
        assertEquals("user_name44444", userDbStorage.getUser(updatedTestUser.getId()).getName());
    }

    @Test
    public void shouldNotUpdateUser() {
        User testUser = testUserBuilder(9999, "test4@mail.com", "test_login4",
                "user_name4", LocalDate.of(2020, 4, 4));

        assertThrows(NotPresentException.class, () -> userDbStorage.updateUser(testUser));
    }

    @Test
    public void shouldDeleteUser() {
        User testUser = testUserBuilder(4, "test4@mail.com", "test_login4",
                "user_name4", LocalDate.of(2020, 4, 4));
        userDbStorage.addUser(testUser);
        assertEquals(testUser, userDbStorage.getUser(4));

        userDbStorage.removeUser(testUser);
        assertThrows(NotPresentException.class, () -> userDbStorage.getUser(testUser.getId()));
    }

    @Test
    public void shouldNotDeleteUser() {
        User testUser = testUserBuilder(4, "test4@mail.com", "test_login4",
                "user_name4", LocalDate.of(2020, 4, 4));

        assertThrows(NotPresentException.class, () -> userDbStorage.removeUser(testUser));
    }
}
