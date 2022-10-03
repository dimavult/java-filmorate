package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    public UserController userController;
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
        userController = new UserController();
    }

    @Test
    @DisplayName("test GET /users")
    void getUsers() {
        assertEquals(0, userController.getUsers().size(), "users list is not empty");

        userController.saveUser(correctUser);

        assertEquals(1, userController.getUsers().size(), "user wasn't added");
    }

    @Test
    @DisplayName("test POST /users")
    void saveUser() {
        /*
        test with correct user
         */
        userController.saveUser(correctUser);

        assertEquals(1, userController.getUsers().size(), "user wasn't added");
        /*
        tests with users with incorrect email
         */
        assertThrows(ValidationException.class, () -> userController.saveUser(userWIthNullEmail),
                "user with null email has been added");
        assertThrows(ValidationException.class, () -> userController.saveUser(userWIthEmptyEmail),
                "user with empty email has been added");
        assertThrows(ValidationException.class, () -> userController.saveUser(userWIthEmailWithoutNeededSymbol),
                "user without '@' symbol has been added");
        /*
        tests with users with incorrect login
         */
        assertThrows(ValidationException.class, () -> userController.saveUser(userWithNullLogin),
                "user with null login has been added");
        assertThrows(ValidationException.class, () -> userController.saveUser(userWithEmptyLogin),
                "user with empty login has been added");
        assertThrows(ValidationException.class, () -> userController.saveUser(userWithLoginContainsSpaces),
                "user with login containing spaces has been added");
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
        assertThrows(ValidationException.class, () -> userController.saveUser(userWithBirthdayInFuture),
                "birthday cannot be in the future");
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

        assertEquals(anotherName, userController.getUsers().get(0).getName(),
                "names are not the same");
        assertEquals(anotherLogin, userController.getUsers().get(0).getLogin(),
                "logins are not the same");
        assertEquals(anotherEmail, userController.getUsers().get(0).getEmail(),
                "emails are not the same");
        assertEquals(anotherBirthday, userController.getUsers().get(0).getBirthday(),
                "dates are not the same");

        assertThrows(ValidationException.class, ()-> userController.updateUser(notAddedUser));
    }
}