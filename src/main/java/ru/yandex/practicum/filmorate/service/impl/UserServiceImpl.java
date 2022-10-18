package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserStorage storage;

    @Autowired
    public UserServiceImpl(UserStorage storage) {
        this.storage = storage;
    }

    @Override
    public User addFriend(long yourId, long friendId) {
        storage.getUserById(friendId).addFriend(yourId);
        storage.getUserById(yourId).addFriend(friendId);
        return storage.getUserById(friendId);
    }

    @Override
    public User deleteFriend(long yourId, long friendId) {
        storage.getUserById(friendId).deleteFriend(yourId);
        storage.getUserById(yourId).deleteFriend(friendId);
        return storage.getUserById(friendId);
    }

    @Override
    public List<User> getUsersFriends(long id) {
        return storage.getUserById(id).getFriends().stream()
                .map(storage::getUserById)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getMutualFriends(long yourId, long friendId) {
        return getFriendsIds(yourId).stream()
                .filter(getFriendsIds(friendId)::contains)
                .map(storage::getUserById)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsers() {
        return storage.getUsers();
    }

    @Override
    public void deleteUser(long id) {
        storage.deleteUser(id);
    }

    @Override
    public User addUser(User user) {
        return storage.addUser(user);
    }

    @Override
    public User updateUser(User user) {
        return storage.updateUser(user);
    }

    @Override
    public User getUserById(long id) {
        return storage.getUserById(id);
    }

    private Set<Long> getFriendsIds(long id) {
        return storage.getUserById(id).getFriends();
    }
}
