package ru.yandex.practicum.filmorate.storage.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long id = 1;

    private long generateId() {
        return id++;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(long id) {
        if (!users.containsKey(id)) throw new UserNotFoundException("User not found");
        return users.get(id);
    }

    @Override
    public void deleteUser(long id) {
        if (!users.containsKey(id)) throw new UserNotFoundException("User not found");
        users.remove(id);
    }

    @Override
    public User addUser(User user) {
        addNonameUser(user);
        log.info("add user - " + user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) throw new UserNotFoundException("User doesn't exist");
        long userId = user.getId();
        users.put(userId, user);
        log.info("update user - " + user);

        return user;
    }

    private void addNonameUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            String login = user.getLogin();
            user.setName(login);
        }

        user.setId(generateId());
        users.put(user.getId(), user);
    }
}
