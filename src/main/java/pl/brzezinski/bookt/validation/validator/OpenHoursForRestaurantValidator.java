package pl.brzezinski.bookt.validation.validator;

import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.validation.constraint.OpenHoursForRestaurant;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OpenHoursForRestaurantValidator implements ConstraintValidator<OpenHoursForRestaurant, Restaurant> {

    @Override
    public void initialize(OpenHoursForRestaurant constraintAnnotation) {

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
