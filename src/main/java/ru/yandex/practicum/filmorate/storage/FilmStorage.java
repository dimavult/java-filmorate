package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> getFilms();
    Film getFilmById(long id);
    void deleteFilm(long id);
    Film addFilm(Film film);
    Film updateFilm(Film film);
    List<Film> getTopFilms(int count);
}
