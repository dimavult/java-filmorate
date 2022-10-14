package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import utils.ValidatorTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    public FilmController filmController;
    public FilmService filmService;
    public final Film correctFilm = new Film("film",
            "desc",
            LocalDate.of(1956, 4, 1),
            95L);
    public final Film filmWithoutName = new Film(null,
            "desc1",
            LocalDate.of(1956, 4, 1),
            95L);
    public final Film filmWithEmptyName = new Film("",
            "desc2",
            LocalDate.of(1956, 4, 1),
            95L);
    public final Film filmWithIncorrectReleaseDate = new Film("film3",
            "desc1",
            LocalDate.of(1356, 4, 1),
            95L);
    public final Film filmWIthTooLongDesc = new Film("film4",
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
            95L);
    public final Film filmWIth199SymbolsDesc = new Film("film5",
            "тексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттекс",
            LocalDate.of(1956, 4, 1),
            95L);
    public final Film filmWIth200SymbolsDesc = new Film("film6",
            "тексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттексt",
            LocalDate.of(1956, 4, 1),
            95L);
    public final Film filmWIth201SymbolsDesc = new Film("film7",
            "тексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттексттексттексттексттекстт" +
                    "ексттексттексттексттексттексттексттексттексtt",
            LocalDate.of(1956, 4, 1),
            95L);
    public final Film filmWithReleaseDateEqualsEarliest = new Film("film8",
            "desc1",
            LocalDate.of(1895, 12, 28),
            95L);
    public final Film notAddedFilm = new Film(123123123,
            "film",
            "desc",
            LocalDate.of(1956, 4, 1),
            95L);

    @BeforeEach
    public void setup() {
        filmController = new FilmController(filmService);
    }

    @Test
    @DisplayName("test GET /films endpoint")
    void getFilms() {
        assertEquals(0, filmController.getFilms().size(), "films list is not empty");

        filmController.saveFilm(correctFilm);

        assertEquals(1, filmController.getFilms().size(), "film wasn't added");
    }

    @Test
    @DisplayName("test POST /films endpoint")
    void saveFilm() {
        /*
        test with valid fields
         */
        assertFalse(ValidatorTestUtils.objHasErrorMessage(correctFilm,
                "incorrect object data"));
        /*
        test with incorrect name
         */
        assertTrue(ValidatorTestUtils.objHasErrorMessage(filmWithoutName,
                "Name is mandatory"));
        assertTrue(ValidatorTestUtils.objHasErrorMessage(filmWithEmptyName,
                "Name is mandatory"));
        /*
        tests with description
         */
        assertTrue(ValidatorTestUtils.objHasErrorMessage(filmWIthTooLongDesc,
                "Max size of description - 200 symbols"));
        assertFalse(ValidatorTestUtils.objHasErrorMessage(filmWIth199SymbolsDesc,
                "Max size of description - 200 symbols"));
        assertFalse(ValidatorTestUtils.objHasErrorMessage(filmWIth200SymbolsDesc,
                "Max size of description - 200 symbols"));
        assertTrue(ValidatorTestUtils.objHasErrorMessage(filmWIth201SymbolsDesc,
                "Max size of description - 200 symbols"));
        /*
        test with incorrect release date
         */
        assertTrue(ValidatorTestUtils.objHasErrorMessage(filmWithIncorrectReleaseDate,
                "Release date should be after 28.12.1895"));
        assertFalse(ValidatorTestUtils.objHasErrorMessage(filmWithReleaseDateEqualsEarliest,
                "incorrect object data"));
    }

    @Test
    @DisplayName("test PUT /films endpoint")
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