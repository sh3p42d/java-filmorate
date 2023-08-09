package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.helper.BuildUtil;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDaoTest extends BuildUtil {
    private final MpaDbStorage mpaDbStorage;

    @Test
    public void shouldGetMpaById() {
        Mpa mpa = testMpaBuilder(1, "G");
        assertEquals(mpa, mpaDbStorage.getMpaById(1));
    }

    @Test
    public void shouldGetAllMpa() {
        Mpa mpa1 = testMpaBuilder(1, "G");
        Mpa mpa2 = testMpaBuilder(2, "PG");
        Mpa mpa3 = testMpaBuilder(3, "PG-13");
        Mpa mpa4 = testMpaBuilder(4, "R");
        Mpa mpa5 = testMpaBuilder(5, "NC-17");

        List<Mpa> mpaList = new ArrayList<>();
        mpaList.add(mpa1);
        mpaList.add(mpa2);
        mpaList.add(mpa3);
        mpaList.add(mpa4);
        mpaList.add(mpa5);

        assertEquals(mpaList, mpaDbStorage.getAllMpa());
    }
}
