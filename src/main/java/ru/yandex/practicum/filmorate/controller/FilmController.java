package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.filmvalidator.FilmValidator;

import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private final FilmValidator filmValidator = new FilmValidator();
    private int id = 1;
    private int generateId() {
        return id++;
    }
    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film saveFilm(@RequestBody Film film) {
        if (filmValidator.validate(film)) {
            film.setId(generateId());
            films.put(film.getId(), film);
            log.info("add film - " + film);
        } else {
            throw new ValidationException("A validation error occurred");
        }
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (filmValidator.validate(film) && films.containsKey(film.getId())) {
            int filmId = film.getId();
            films.put(filmId, film);
            log.info("update film - " + film);
        } else {
            throw new ValidationException("A validation error occurred");
        }
        return film;
    }
}
