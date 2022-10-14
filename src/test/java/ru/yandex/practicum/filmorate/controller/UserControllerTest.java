package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.service.impl.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.imp.InMemoryUserStorage;
import utils.ValidatorTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    public UserController userController;
    public UserService userService;
    public User correctUser = new User("somemail@gmail.com",
            "login",
            "name",
            LocalDate.of(1999,6,7));
    public User userWIthEmptyEmail = new User("",
            "login",
            "name",
            LocalDate.of(1999,6,7));
    public User userWIthNullEmail = new User(null,
            "login",
            "name",
            LocalDate.of(1999,6,7));
    public User userWIthEmailWithoutNeededSymbol = new User("somemailgmail.com",
            "login",
            "name",
            LocalDate.of(1999,6,7));
    public User userWithNullLogin = new User("somemail@gmail.com",
            null,
            "name",
            LocalDate.of(1999,6,7));
    public User userWithEmptyLogin = new User("somemail@gmail.com",
            "",
            "name",
            LocalDate.of(1999,6,7));
    public User userWithLoginContainsSpaces = new User("somemail@gmail.com",
            "l o g i n ",
            "name",
            LocalDate.of(1999,6,7));
    public User userWithoutName = new User("somemail@gmail.com",
            "login",
            LocalDate.of(1999,6,7));
    public User userWithBirthdayInFuture = new User("somemail@gmail.com",
            "login",
            "name",
            LocalDate.now().plusDays(1));
    public User notAddedUser = new User(412412,"somemail@gmail.com",
            "login",
            "name",
            LocalDate.now().plusDays(1));

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(new InMemoryUserStorage());
        userController = new UserController(userService);
    }

    @Test
    @DisplayName("test GET /users")
    void getUsers() {
        assertEquals(0, userController.getUsersList().size(), "users list is not empty");

        userController.saveUser(correctUser);

        assertEquals(1, userController.getUsersList().size(), "user wasn't added");
    }

    @Test
    @DisplayName("test POST /users")
    void saveUser() {
        /*
        tests with users with incorrect email
         */
        assertTrue(ValidatorTestUtils.objHasErrorMessage(userWIthNullEmail,
                "Email is mandatory"));
        assertTrue(ValidatorTestUtils.objHasErrorMessage(userWIthEmptyEmail,
                "Email is mandatory"));
        assertTrue(ValidatorTestUtils.objHasErrorMessage(userWIthEmailWithoutNeededSymbol,
                "Wrong email"));
        /*
        tests with users with incorrect login
         */
        assertTrue(ValidatorTestUtils.objHasErrorMessage(userWithNullLogin,
                "Login is mandatory"));
        assertTrue(ValidatorTestUtils.objHasErrorMessage(userWithEmptyLogin,
                "Login is mandatory"));
        assertTrue(ValidatorTestUtils.objHasErrorMessage(userWithLoginContainsSpaces,
                "Login mustn't contain spaces"));
        /*
        test with nameless user
         */
        userController.saveUser(userWithoutName);
        String name = userWithoutName.getName();
        String login = userWithoutName.getLogin();
        assertEquals(name, login, "if user has no name, login must be his name");
        /*
        test with incorrect birthday
         */
        assertTrue(ValidatorTestUtils.objHasErrorMessage(userWithBirthdayInFuture,
                "Birthday must be in past"));
    }

    @Test
    @DisplayName("test PUT /users")
    void updateUser() {
        userController.saveUser(correctUser);
        final String anotherName = "another name";
        final String anotherLogin = "anotherLogin";
        final String anotherEmail = "another.mail@gmail.com";
        final LocalDate anotherBirthday = LocalDate.of(2000,1,20);
        correctUser.setName(anotherName);
        correctUser.setLogin(anotherLogin);
        correctUser.setEmail(anotherEmail);
        correctUser.setBirthday(anotherBirthday);

        assertEquals(anotherName, userController.getUsersList().get(0).getName(),
                "names are not the same");
        assertEquals(anotherLogin, userController.getUsersList().get(0).getLogin(),
                "logins are not the same");
        assertEquals(anotherEmail, userController.getUsersList().get(0).getEmail(),
                "emails are not the same");
        assertEquals(anotherBirthday, userController.getUsersList().get(0).getBirthday(),
                "dates are not the same");

        assertThrows(UserNotFoundException.class, ()-> userController.updateUser(notAddedUser));
    }
}