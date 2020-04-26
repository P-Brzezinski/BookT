package pl.brzezinski.bookt.validation.validator;

import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.validation.constraint.CloseTimeValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CloseTimeValidator implements ConstraintValidator<CloseTimeValid, Restaurant> {

    @Override
    public void initialize(CloseTimeValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(Restaurant restaurant, ConstraintValidatorContext constraintValidatorContext) {

        if (restaurant.getOpenTime() != null && restaurant.getCloseTime() != null){
            return restaurant.getOpenTime().isBefore(restaurant.getCloseTime());
        }else {
            return false;
        }
    }
}
