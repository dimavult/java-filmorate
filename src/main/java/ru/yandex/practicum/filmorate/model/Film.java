package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.annotation.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Film {
    private long id;
    private Set<Long> likes = new HashSet<>();
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Name cant be null")
    @Size(max = 200, message = "Max size of description - 200 symbols")
    private String description;
    @NotNull(message = "Release date is mandatory")
    @ReleaseDate(message = "Release date should be after 28.12.1895")
    private LocalDate releaseDate;
    @NotNull(message = "Duration is mandatory")
    @Positive(message = "Duration must be positive number")
    private Long duration;

    public Film(long id, String name, String description, LocalDate releaseDate, Long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
    public Film(String name, String description, LocalDate releaseDate, long duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public void addLike(long id) {
        likes.add(id);
    }

    public void removeLike(long id) {
        likes.remove(id);
    }
}
