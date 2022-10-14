package ru.yandex.practicum.filmorate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.LikeNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
@Component
public class FilmServiceImpl implements FilmService {
    private final FilmStorage storage;

    @Autowired
    public FilmServiceImpl(FilmStorage storage) {
        this.storage = storage;
    }

    @Override
    public void likeFilm(long filmId, long userId) {
        storage.getFilmById(filmId).addLike(userId);
    }

    @Override
    public Film deleteLike(long filmId, long userId) {
        if (!storage.getFilmById(filmId).getLikes().contains(userId)) throw new LikeNotFoundException("Like not found");
        storage.getFilmById(filmId).removeLike(userId);
        return storage.getFilmById(filmId);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return storage.getTopFilms(count);
    }

    @Override
    public List<Film> getFilms() {
        return storage.getFilms();
    }

    @Override
    public Film getFilmById(long id) {
        return storage.getFilmById(id);
    }

    @Override
    public void deleteFilm(long id) {
        storage.deleteFilm(id);
    }

    @Override
    public Film addFilm(Film film) {
        return storage.addFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        return storage.updateFilm(film);
    }
}
