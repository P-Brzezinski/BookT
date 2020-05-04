package pl.brzezinski.bookt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;

import java.util.List;

public interface SchemaTableRepository extends JpaRepository<SchemaTable, Long> {

    List<SchemaTable> findAllByRestaurant(Restaurant restaurant);
    List<SchemaTable> findAllByPlaces(int places);
    List<SchemaTable> findAllByRestaurantAndPlaces(Restaurant restaurant, int places);
    List<SchemaTable> findAllByRestaurantAndPlacesBetween(Restaurant restaurant, int min, int max);
    SchemaTable findByRestaurantAndAndTableNumber(Restaurant restaurant, int tableNumber);
}
