package ru.yandex.practicum.filmorate.storage.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long id = 1;
    private long generateId() {
        return id++;
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(long id) {
        if (!films.containsKey(id)) throw new FilmNotFoundException("Film not found");
        return films.get(id);
    }

    @Override
    public void deleteFilm(long id) {
        if (!films.containsKey(id)) throw new FilmNotFoundException("Film not found");
        films.remove(id);
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("add film - " + film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) throw new FilmNotFoundException("Film not found");
        films.put(film.getId(), film);
        log.info("add film - " + film);
        return film;
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return films.values().stream()
                .sorted((f1, f2) -> {
                    Integer f1size = f1.getLikes().size();
                    Integer f2size = f2.getLikes().size();
                    int comp = f1size.compareTo(f2size);
                    return -1 * comp;
                })
                .limit(count)
                .collect(Collectors.toList());
    }
}
