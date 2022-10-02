package ru.yandex.practicum.filmorate.validator.filmvalidator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class FilmValidator {
    private final static LocalDate EARLIEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public boolean validate(Film film) {
        if (film == null) {
            log.debug("film is null");
            return false;
        } else if (film.getName() == null || film.getName().isBlank()) {
            log.debug("film name is mandatory");
            return false;
        } else if (film.getReleaseDate().isBefore(EARLIEST_RELEASE_DATE)) {
            log.debug("wrong release date");
            return false;
        } else if (film.getDuration() <= 0) {
            log.debug("wrong film duration");
            return false;
        } else if (film.getDescription().length() > 200) {
            log.debug("too long description");
            return false;
        }
        return true;
    }
}
