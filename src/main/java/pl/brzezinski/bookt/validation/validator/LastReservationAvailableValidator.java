package pl.brzezinski.bookt.validation.validator;

import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.validation.constraint.LastReservationAvailable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class LastReservationAvailableValidator implements ConstraintValidator<LastReservationAvailable, Reservation> {

    @Override
    public void initialize(LastReservationAvailable constraintAnnotation) {
    }

    @Override
    public boolean isValid(Reservation reservation, ConstraintValidatorContext constraintValidatorContext) {
        LocalTime reservationTime = reservation.getDateTime().toLocalTime();
        LocalTime closeTimeForRestaurant = reservation.getRestaurant().getCloseTime();
        int estimatedTimeForOneReservation = reservation.getRestaurant().ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_MINUTES;
        return reservationTime.isBefore(closeTimeForRestaurant.minusHours(estimatedTimeForOneReservation).plusMinutes(1));
    }
}
