import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.stream.Stream;

public class FilmControllerTest extends ControllerTest<Film> {
    @BeforeEach
    public void beforeEach() {
        url = "/films";
        date = LocalDate.parse("2222-02-22");
        valueForTest = getValidValueForTest();
        valueForPostTest = getValueForPostTest();
    }

    @Override
    protected Film getValidValueForTest() {
        return Film.builder()
                .name("Nolan is a genius")
                .description("Genius create another great movie")
                .releaseDate(date)
                .duration(180)
                .build();
    }

    @Override
    protected Film getValueForPostTest() {
        return Film.builder()
                .name("Genius is Nolan")
                .description("Genius create another great movie")
                .releaseDate(date)
                .duration(180)
                .build();
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
    protected String valueForTestToString(Film film) {
        return "{\"id\":" + film.getId() +
                ",\"name\":\"" + film.getName() +
                "\",\"description\":\"" + film.getDescription() +
                "\",\"releaseDate\":\"" + film.getReleaseDate() +
                "\",\"duration\":" + film.getDuration() + "}";
    }

    protected static Stream<Arguments> invalidFields() {
        return Stream.of(
                Arguments.of("name", " "),
                Arguments.of("name", null),
                Arguments.of("description",
                        "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"),
                Arguments.of("releaseDate", LocalDate.parse("1800-01-01")),
                Arguments.of("duration", -100)
        );
    }
}

