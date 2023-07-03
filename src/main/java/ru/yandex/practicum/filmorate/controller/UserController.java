package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends RequestController<User> {

    @Override
    protected boolean checkIsExist(User user) {
        return mapOfInfo.containsKey(user.getId());
    }

    @Override
    protected int extractId(User user) {
        return user.getId();
    }

    @Override
    protected void addId(User user, int id) {
        user.setId(id);
    }
}
