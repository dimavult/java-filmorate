package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private final Set<Long> friends = new HashSet<>();
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Wrong email")
    private String email;

    @NotBlank(message = "Login is mandatory")
    @Pattern(regexp = "\\S+", message = "Login mustn't contain spaces")
    private String login;
    private String name;

    @NotNull(message = "Birthday is mandatory")
    @Past(message = "Birthday must be in past")
    private LocalDate birthday;

    public void addFriend(long id){
        friends.add(id);
    }

    public void deleteFriend(long id) {
        friends.remove(id);
    }

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User(String email, String login, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
}
