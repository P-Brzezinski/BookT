package pl.brzezinski.bookt.validation.validator;

import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.validation.constraint.OpenHoursForReservation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class OpenHoursForReservationValidator implements ConstraintValidator<OpenHoursForReservation, Reservation> {

    @Override
    public void initialize(OpenHoursForReservation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Reservation reservation, ConstraintValidatorContext constraintValidatorContext) {
        LocalTime reservationTime = reservation.getDateTime().toLocalTime();
        return reservationTime.isAfter(reservation.getRestaurant().getOpenTime())
                && reservationTime.isBefore(reservation.getRestaurant().getCloseTime().minusHours(reservation.getRestaurant().ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_HOURS).plusMinutes(1));
    }
}

