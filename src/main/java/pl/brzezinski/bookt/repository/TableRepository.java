package pl.brzezinski.bookt.repository;

import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SingleTable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<SingleTable, Long> {

    List<SingleTable> findAllByRestaurant(Restaurant restaurant);

    List<SingleTable> findAllByPlacesAndDateOfReservationAndRestaurant(Long places, LocalDateTime dateOfReservation, Restaurant restaurant);

    SingleTable findFirstByTableNumberIsNot(Long tableNumber);

    List<SingleTable> findAllByDateOfReservationBeforeAndDateOfReservationIsAfter(LocalDateTime isBefore, LocalDateTime isAfter);

    List<SingleTable> findAllByRestaurantAndPlacesAndAndDateOfReservationIsBetween(Restaurant restaurant, Long places, LocalDateTime downTime, LocalDateTime topTime);

    SingleTable findFirstByDateOfReservationIsBefore(LocalDateTime dateTime);

//    SingleTable findFirstByRestaurantAndPlacesAndAndDateOfReservationIsBefore(Restaurant restaurant, Long places, LocalDateTime dateTime);
//
//    SingleTable findFirstByRestaurantAndPlacesAndAndDateOfReservationIsAfter(Restaurant restaurant, Long places, LocalDateTime dateTime);

    SingleTable findFirstByRestaurantAndTableNumberAndAndDateOfReservationBefore(Restaurant restaurant, Long tableNumber, LocalDateTime dateOfReservation);
    SingleTable findFirstByRestaurantAndTableNumberAndAndDateOfReservationAfter(Restaurant restaurant, Long tableNumber, LocalDateTime dateOfReservation);
}

