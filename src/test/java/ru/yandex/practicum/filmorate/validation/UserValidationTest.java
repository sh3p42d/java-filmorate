package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.stream.Stream;

class UserValidationTest extends ValidationTest<User> {
    @BeforeEach
    public void beforeEach() {
        date = LocalDate.parse("2000-01-10");
        valueForTest = getValidValueForTest();
    }

    @Override
    protected User getValidValueForTest() {
        return User.builder()
                .id(1)
                .email("email@mail.com")
                .login("login123")
                .birthday(date)
                .name("Kama Pulya")
                .build();
    }

    protected static Stream<Arguments> invalidFields() {
        return Stream.of(
                Arguments.of("email", "@qwe.com"),
                Arguments.of("email", null),
                Arguments.of("login", "qwe zxc"),
                Arguments.of("login", null),
                Arguments.of("birthday", LocalDate.now().plusDays(1))
        );
    }
}
