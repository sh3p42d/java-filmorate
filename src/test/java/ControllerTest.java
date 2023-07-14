import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.stream.Stream;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.FilmorateApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureMockMvc
public abstract class ControllerTest<T> {
    protected String url;
    protected LocalDate date;
    protected T valueForTest;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected T getValidValueForTest() {
        return null;
    }

    protected void setValueForTestId(int id) {

    }

    protected String valueForTestToString(T valueForTest) {
        return null;
    }

    @Test
    void getEmptyList() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                    .get(url)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals("[]", result.getResponse().getContentAsString());
    }

    @Test
    void getValueList() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                    .post(url).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(valueForTest)));

        setValueForTestId(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals("[" + valueForTestToString(valueForTest) + "]", result.getResponse().getContentAsString());
    }

    protected static Stream<Arguments> invalidFields() {
        return null;
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("invalidFields")
    void postInvalidValue(String fieldName, Object invalidValue) {
        Field field = valueForTest.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(valueForTest, invalidValue);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(valueForTest))).andReturn();

        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void postEmpty() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(url).contentType(MediaType.APPLICATION_JSON)
                .content("")).andReturn();

        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void postValid() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(valueForTest))).andReturn();

        assertEquals(201, result.getResponse().getStatus());
        setValueForTestId(2);
        assertEquals(valueForTestToString(valueForTest), result.getResponse().getContentAsString());
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("invalidFields")
    void putInvalidValue(String fieldName, Object invalidValue) {
        Field field = valueForTest.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(valueForTest, invalidValue);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .put(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(valueForTest))).andReturn();

        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void putEmpty() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .put(url).contentType(MediaType.APPLICATION_JSON)
                .content("")).andReturn();

        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void putValid() throws Exception {
        setValueForTestId(1);
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .put(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(valueForTest))).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals(valueForTestToString(valueForTest), result.getResponse().getContentAsString());
    }
}