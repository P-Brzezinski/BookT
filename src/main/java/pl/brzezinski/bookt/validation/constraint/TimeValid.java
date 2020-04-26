package pl.brzezinski.bookt.validation.constraint;

import pl.brzezinski.bookt.validation.validator.TimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = {TimeValidator.class})
public @interface TimeValid {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
