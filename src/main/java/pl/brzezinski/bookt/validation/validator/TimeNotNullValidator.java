package pl.brzezinski.bookt.validation.validator;

import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.validation.constraint.TimeNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeNotNullValidator implements ConstraintValidator<TimeNotNull, Restaurant> {

    @Override
    public void initialize(TimeNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(Restaurant restaurant, ConstraintValidatorContext constraintValidatorContext) {
        if (restaurant.getOpenTime() == null || restaurant.getCloseTime() == null){
            return false;
        }
        return true;
    }
}
