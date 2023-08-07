import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.stream.Stream;

class FilmValidationTest extends ValidationTest<Film> {
    @BeforeEach
    public void beforeEach() {
        date = LocalDate.parse("2222-02-22");
        valueForTest = getValidValueForTest();
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
