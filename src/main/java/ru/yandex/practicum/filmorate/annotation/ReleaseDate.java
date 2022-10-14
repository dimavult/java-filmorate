package ru.yandex.practicum.filmorate.annotation;

import ru.yandex.practicum.filmorate.annotation.impl.ReleaseDateImp;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseDateImp.class)
public @interface ReleaseDate { //добавил на будущее, когда сможешь нормально обработать валидацию(надеюсь)
    String message() default "Release date must be after 28.12.1895";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
