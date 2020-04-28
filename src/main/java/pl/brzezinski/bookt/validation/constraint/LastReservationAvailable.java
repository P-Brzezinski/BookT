package pl.brzezinski.bookt.validation.constraint;

import pl.brzezinski.bookt.validation.validator.BadWordValidator;
import pl.brzezinski.bookt.validation.validator.LastReservationAvailableValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {LastReservationAvailableValidator.class})
@Target(TYPE)
@Retention(RUNTIME)
public @interface LastReservationAvailable {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}