package ru.yandex.practicum.filmorate.validator.uservalidator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class UserValidator {

    public boolean validate(User user) {
        if (user == null) {
            log.debug("user is null");
            return false;
        } else if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.debug("empty email");
            return false;
        } else if (!user.getEmail().contains("@")) {
            log.debug("wrong email");
            return false;
        } else if (user.getLogin() == null || user.getLogin().isEmpty()) {
            log.debug("wrong login");
            return false;
        } else if (user.getLogin().contains(" ")) {
            log.debug("login contains space/es");
            return false;
        } else return !user.getBirthday().isAfter(LocalDate.now());
    }
}
