package pl.brzezinski.bookt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SingleTable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<SingleTable, Long> {

    List<SingleTable> findAllByRestaurant(Restaurant restaurant);

    List<SingleTable> findAllByPlacesAndDateOfReservationAndRestaurant(Long places, LocalDateTime dateOfReservation, Restaurant restaurant);

    List<SingleTable> findAllByPlacesAndDateOfReservationAndRestaurantAndTableNumber(Long places, LocalDateTime dateOfReservation, Restaurant restaurant, Long tableNUmber);

}

