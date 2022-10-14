package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> getUsers();
    void deleteUser(long id);
    User addUser(User user);
    User updateUser(User user);
    User getUserById(long id);
}
