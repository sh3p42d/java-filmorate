package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.helper.BuildUtil;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeDaoTest extends BuildUtil {
    private final LikeDbStorage likeDbStorage;

    @Test
    public void userShouldLikeFilm() {
        User user = testUserBuilder(1, "test1@mail.com", "test_login1",
                "user_name1", LocalDate.of(2021, 1, 1));
        Film filmWithoutLike = testFilmBuilder(1, "I love Linux", "Help me",
                LocalDate.of(1951, 1, 1), 90, 1, "G");
        Film film = testFilmBuilder(2, "Qwe", "asd",
                LocalDate.of(1952, 2, 2), 90, 2, "PG");

        assertEquals(filmWithoutLike, likeDbStorage.getPopular(10).get(0));
        likeDbStorage.like(film.getId(), user.getId());
        assertEquals(film, likeDbStorage.getPopular(10).get(0));
        assertEquals(filmWithoutLike, likeDbStorage.getPopular(10).get(1));
    }

    @Test
    public void userShouldUnlikeFilm() {
        User user = testUserBuilder(1, "test1@mail.com", "test_login1",
                "user_name1", LocalDate.of(2021, 1, 1));
        Film filmWithoutLike = testFilmBuilder(1, "I love Linux", "Help me",
                LocalDate.of(1951, 1, 1), 90, 1, "G");
        Film film = testFilmBuilder(2, "Qwe", "asd",
                LocalDate.of(1952, 2, 2), 90, 2, "PG");

        likeDbStorage.like(film.getId(), user.getId());
        assertEquals(film, likeDbStorage.getPopular(10).get(0));
        assertEquals(filmWithoutLike, likeDbStorage.getPopular(10).get(1));

        likeDbStorage.unlike(film.getId(), user.getId());
        assertEquals(filmWithoutLike, likeDbStorage.getPopular(10).get(0));
        assertEquals(film, likeDbStorage.getPopular(10).get(1));
    }
}

