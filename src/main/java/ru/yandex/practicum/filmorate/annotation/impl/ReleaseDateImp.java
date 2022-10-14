package ru.yandex.practicum.filmorate.annotation.impl;

import ru.yandex.practicum.filmorate.annotation.ReleaseDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateImp implements ConstraintValidator<ReleaseDate, LocalDate> {

    private final static LocalDate EARLIEST_RELEASE_DAY = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext){
        return localDate.isAfter(EARLIEST_RELEASE_DAY);
    }
}
