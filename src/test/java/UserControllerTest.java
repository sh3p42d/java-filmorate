import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.stream.Stream;

public class UserControllerTest extends ControllerTest<User> {
    @BeforeEach
    public void beforeEach() {
        url = "/users";
        date = LocalDate.parse("2000-01-10");
        valueForTest = getValidValueForTest();
        valueForPostTest = getValueForPostTest();
    }

    @Override
    protected User getValidValueForTest() {
        return new User("some@mail.com", "someLogin", date);
    }

    @Override
    protected User getValueForPostTest() {
        return new User("test@mail.com", "test", date);
    }

    @Override
    protected void setValueForTestId(int id) {
        valueForTest.setId(id);
    }

    @Override
    protected void setValueForPostTest(int id) {
        valueForPostTest.setId(id);
    }

    @Override
    protected String valueForTestToString(User user) {
        return "{\"id\":" + user.getId() +
                ",\"email\":\"" + user.getEmail() +
                "\",\"login\":\"" + user.getLogin() +
                "\",\"name\":\"" + user.getName() +
                "\",\"birthday\":\"" + user.getBirthday() + "\"}";
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
