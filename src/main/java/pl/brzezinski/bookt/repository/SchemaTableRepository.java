package pl.brzezinski.bookt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.tables.SchemaTable;

import java.util.List;

public interface SchemaTableRepository extends JpaRepository<SchemaTable, Long> {

    List<SchemaTable> findAllByRestaurant(Restaurant restaurant);
    List<SchemaTable> findAllByRestaurantAndPlacesBetween(Restaurant restaurant, int min, int max);
    SchemaTable findByRestaurantAndAndTableNumber(Restaurant restaurant, int tableNumber);
}
