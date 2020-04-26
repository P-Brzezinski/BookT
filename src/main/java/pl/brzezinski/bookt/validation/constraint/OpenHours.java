package pl.brzezinski.bookt.validation.constraint;

import pl.brzezinski.bookt.validation.validator.OpenHoursValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = {OpenHoursValidator.class})
public @interface OpenHours {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
