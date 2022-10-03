package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.uservalidator.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private final UserValidator validator = new UserValidator();
    private int id = 1;
    private int generateId() {
        return id++;
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        if (validator.validate(user)) {
            addUser(user);
            log.info("add user - " + user);
        } else {
            throw new ValidationException("A validation error occurred");
        }
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (validator.validate(user) && users.containsKey(user.getId())) {
            int userId = user.getId();
            users.put(userId, user);
            log.info("update user - " + user);
        } else {
            throw new ValidationException("A validation error occurred");
        }
        return user;
    }

    private void addUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            String login = user.getLogin();
            user.setName(login);
        }

        user.setId(generateId());
        users.put(user.getId(), user);
    }
}
