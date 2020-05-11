package pl.brzezinski.bookt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.bookt.model.tables.ReservedTable;
import pl.brzezinski.bookt.model.Restaurant;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservedTableRepository extends JpaRepository<ReservedTable, Long> {

    List<ReservedTable> findAllByRestaurantAndTableNumberAndDateOfReservationIsBefore(Restaurant restaurant, int tableNumber, LocalDateTime dateTime);
    List<ReservedTable> findAllByRestaurantAndTableNumberAndDateOfReservationIsAfter(Restaurant restaurant, int tableNumber, LocalDateTime dateTime);
    ReservedTable findByRestaurantAndTableNumberAndDateOfReservation(Restaurant restaurant, int tableNumber, LocalDateTime dateTime);

}

