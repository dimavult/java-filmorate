package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService storage) {
        this.service = storage;
    }

    @GetMapping
    public List<User> getUsersList() {
        return service.getUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable long id) {
        return service.getUserById(id);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getUsersFriends(@PathVariable long id) {
        return service.getUsersFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> getFriendsIntersection(@PathVariable long id, @PathVariable long otherId) {
        return service.getMutualFriends(id, otherId);
    }

    @PostMapping
    public User saveUser(@Valid @RequestBody User user) {
        return service.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return service.updateUser(user);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public User addFriend(@PathVariable long id, @PathVariable long friendId) {
        return service.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable long id, @PathVariable long friendId) {
        return service.deleteFriend(id, friendId);
    }
}
