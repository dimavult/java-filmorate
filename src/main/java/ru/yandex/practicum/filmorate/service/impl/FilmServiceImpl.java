package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
@Slf4j
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmServiceImpl(FilmStorage storage) {
        this.filmStorage = storage;
    }

    @Override
    public void likeFilm(long filmId, long userId) {
        log.info("User {} liked film {}", userId, filmId);
        filmStorage.getFilmById(filmId).addLike(userId);
    }

    @Override
    public Film deleteLike(long filmId, long userId) {
        if (!filmStorage.getFilmById(filmId).getLikes().contains(userId)) throw new EntityNotFoundException("Like not found");
        log.info("User {} deleted like film {}", userId, filmId);
        filmStorage.getFilmById(filmId).removeLike(userId);
        return filmStorage.getFilmById(filmId);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return filmStorage.getTopFilms(count);
    }

    @Override
    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @Override
    public Film getFilmById(long id) {
        return filmStorage.getFilmById(id);
    }

    @Override
    public void deleteFilm(long id) {
        filmStorage.deleteFilm(id);
    }

    @Override
    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }
}
