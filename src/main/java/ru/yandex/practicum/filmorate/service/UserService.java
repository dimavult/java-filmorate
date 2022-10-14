package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User addFriend(long yourId, long friendId);
    User deleteFriend(long yourId, long friendId);
    List<User> getUsersFriends(long id);
    List<User> getMutualFriends(long yourId, long friendId);
    List<User> getUsers();
    void deleteUser(long id);
    User addUser(User user);
    User updateUser(User user);
    User getUserById(long id);
}
