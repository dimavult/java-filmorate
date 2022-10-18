package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    void likeFilm(long filmId, long userId);
    Film deleteLike(long filmId, long userId);
    List<Film> getTopFilms(int count);
    List<Film> getFilms();
    Film getFilmById(long id);
    void deleteFilm(long id);
    Film addFilm(Film film);
    Film updateFilm(Film film);
}
