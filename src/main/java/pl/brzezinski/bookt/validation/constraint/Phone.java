package pl.brzezinski.bookt.validation.constraint;

import pl.brzezinski.bookt.validation.validator.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
