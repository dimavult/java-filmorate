package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    public FilmController filmController;
    public final Film correctFilm = new Film(1,
            "film",
            "desc",
            LocalDate.of(1956, 4, 1),
            95);
    public final Film filmWithoutName = new Film(2,
            null,
            "desc1",
            LocalDate.of(1956, 4, 1),
            95);
    public final Film filmWithEmptyName = new Film(3,
            "",
            "desc2",
            LocalDate.of(1956, 4, 1),
            95);
    public final Film filmWithIncorrectReleaseDate = new Film(4,
            "film3",
            "desc1",
            LocalDate.of(1356, 4, 1),
            95);
    public final Film filmWIthTooLongDesc = new Film(4,
            "film4",
            "Мой дом везде, где есть небесный свод,\n" +
                    "Где только слышны звуки песен,\n" +
                    "Всё, в чем есть искра жизни, в нём живёт,\n" +
                    "Но для поэта он не тесен.До самых звезд он кровлей досягает\n" +
                    "И от одной стены к другой\n" +
                    "Далёкий путь, который измеряет\n" +
                    "Жилец не взором, но душой, Есть чувство правды в сердце человека,\n" +
                    "Святое вечности зерно:\n" +
                    "Пространство без границ, теченье века\n" +
                    "Объемлет в краткий миг оно.И всемогущим мой прекрасный дом\n" +
                    "Для чувства этого построен,\n" +
                    "И осужден страдать я долго в нём\n" +
                    "И в нём лишь буду я спокоен.",
            LocalDate.of(1956, 4, 1),
            95);
    public final Film filmWIth199SymbolsDesc = new Film(5,
            "film5",
            "тексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттекс",
            LocalDate.of(1956, 4, 1),
            95);
    public final Film filmWIth200SymbolsDesc = new Film(6,
            "film6",
            "тексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттексt",
            LocalDate.of(1956, 4, 1),
            95);
    public final Film filmWIth201SymbolsDesc = new Film(7,
            "film7",
            "тексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттексtt",
            LocalDate.of(1956, 4, 1),
            95);
    public final Film filmWithReleaseDateEqualsEarliest = new Film(8,
            "film8",
            "desc1",
            LocalDate.of(1895, 12, 28),
            95);
    public final Film notAddedFilm = new Film(123123123,
            "film",
            "desc",
            LocalDate.of(1956, 4, 1),
            95);

    @BeforeEach
    public void setup() {
        filmController = new FilmController();
    }

    @Test
    @DisplayName("check GET /films")
    void getFilms() throws IOException, InterruptedException {
        assertEquals(0, filmController.getFilms().size(), "films list is not empty");

        filmController.saveFilm(correctFilm);

        assertEquals(1, filmController.getFilms().size(), "film wasn't added");
    }

    @Test
    @DisplayName("check POST /films")
    void saveFilm() throws IOException, InterruptedException {
        /*
        test with valid fields
         */
        filmController.saveFilm(correctFilm);
        assertEquals(1, filmController.getFilms().size(), "film doesn't added");
        /*
        test with incorrect name
         */
        assertThrows(ValidationException.class,  () -> filmController.saveFilm(filmWithoutName),
                "film with null name has been added");
        assertThrows(ValidationException.class,  () -> filmController.saveFilm(filmWithEmptyName),
                "film with empty name has been added");
        /*
        tests with description
         */
        assertThrows(ValidationException.class, () -> filmController.saveFilm(filmWIthTooLongDesc),
                "film with too long description has been added");
        assertThrows(ValidationException.class, () -> filmController.saveFilm(filmWIth201SymbolsDesc),
                "film with too long description has been added");
        filmController.saveFilm(filmWIth199SymbolsDesc);
        filmController.saveFilm(filmWIth200SymbolsDesc);
        assertEquals(3, filmController.getFilms().size(), "film doesn't added");
        /*
        test with incorrect release date
         */
        assertThrows(ValidationException.class, () -> filmController.saveFilm(filmWithIncorrectReleaseDate),
                "film with incorrect release date has been added");
        filmController.saveFilm(filmWithReleaseDateEqualsEarliest);
        assertEquals(4, filmController.getFilms().size(),
                "film with incorrect release date has been added");

    }

    @Test
    @DisplayName("check PUT /films")
    void updateFilm() {
        filmController.saveFilm(correctFilm);
        final String anotherName = "another name";
        final String anotherDescription = "another description";
        final LocalDate anotherLocalDate = LocalDate.of(2000, 2,2);
        final long anotherDuration = 110;
        correctFilm.setName(anotherName);
        correctFilm.setDescription(anotherDescription);
        correctFilm.setReleaseDate(anotherLocalDate);
        correctFilm.setDuration(anotherDuration);

        assertEquals(anotherName, filmController.getFilms().get(0).getName(),
                "names are not the same");
        assertEquals(anotherDescription, filmController.getFilms().get(0).getDescription(),
                "descriptions are not the same");
        assertEquals(anotherLocalDate, filmController.getFilms().get(0).getReleaseDate(),
                "release dates are not the same");
        assertEquals(anotherDuration, filmController.getFilms().get(0).getDuration(),
                "films duration are not the same");

        assertThrows(ValidationException.class, () -> filmController.updateFilm(notAddedFilm));
    }
}