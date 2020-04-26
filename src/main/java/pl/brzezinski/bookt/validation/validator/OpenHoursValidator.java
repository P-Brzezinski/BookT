package pl.brzezinski.bookt.validation.validator;

import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.validation.constraint.OpenHours;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class OpenHoursValidator implements ConstraintValidator<OpenHours, Reservation> {

    @Override
    public void initialize(OpenHours constraintAnnotation) {

    }

    @Override
    public boolean isValid(Reservation reservation, ConstraintValidatorContext constraintValidatorContext) {
        LocalTime reservationTime = reservation.getDateTime().toLocalTime();
        return reservationTime.isAfter(reservation.getRestaurant().getOpenTime()) && reservationTime.isBefore(reservation.getRestaurant().getCloseTime());
    }
}
