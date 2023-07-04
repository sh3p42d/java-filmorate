package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotPresentException;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public abstract class RequestController<T> {
    protected int id = 1;
    protected final Map<Integer, T> mapOfInfo = new HashMap<>();

    @GetMapping
    public List<T> findAll() {
        return new ArrayList<>(mapOfInfo.values());
    }

    @PostMapping
    public T create(@Valid @RequestBody T typeOfInfo) {
        try {
            addId(typeOfInfo, id);
            mapOfInfo.put(id, typeOfInfo);
            log.info("Добавлен {}: {}", typeOfInfo.getClass(), typeOfInfo);
            id++;
        } catch (ValidationException e) {
            log.error("Ошибка в теле запроса");
        }
        return typeOfInfo;
    }

    @PutMapping
    public T update(@Valid @RequestBody T typeOfInfo) {
        try {
            if (checkIsExist(typeOfInfo)) {
                mapOfInfo.put(extractId(typeOfInfo), typeOfInfo);
                log.info("Обновлен {}: {}", typeOfInfo.getClass(), typeOfInfo);
            } else {
                log.info("{} для обновления не найден", typeOfInfo);
                throw new NotPresentException(HttpStatus.NOT_FOUND, "Нет такого " + typeOfInfo);
            }
        } catch (ValidationException e) {
            log.error("Ошибка в теле запроса");
        }
        return typeOfInfo;
    }

    protected boolean checkIsExist(T typeOfInfo) {
        return false;
    }

    protected int extractId(T typeOfInfo) {
        return 0;
    }

    protected void addId(T typeOfInfo, int id) {

    }
}
