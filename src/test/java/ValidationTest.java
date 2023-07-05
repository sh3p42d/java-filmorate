import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

abstract class ValidationTest<T> {
    protected LocalDate date;
    protected T valueForTest;

    protected T getValidValueForTest() {
        return null;
    }

    @Test
    void shouldCreateValid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<T>> constraintViolations =
                validator.validate(valueForTest);

        assertThat(constraintViolations.size()).isZero();
    }

    protected static Stream<Arguments> invalidFields() {
        return null;
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("invalidFields")
    public void shouldNotCreateInvalid(String fieldName, Object invalidValue) {
        Field field = valueForTest.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(valueForTest, invalidValue);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<T>> constraintViolations =
                validator.validate(valueForTest);

        assertThat(constraintViolations.size()).isOne();
    }
}