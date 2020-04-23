package pl.brzezinski.bookt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;

import java.util.List;

public interface SchemaTableRepository extends JpaRepository<SchemaTable, Long> {

    List<SchemaTable> findAllByRestaurant(Restaurant restaurant);
    List<SchemaTable> findAllByPlaces(Long places);
    List<SchemaTable> findAllByRestaurantAndPlaces(Restaurant restaurant, Long places);
    List<SchemaTable> findAllByRestaurantAndPlacesIsBetween(Restaurant restaurant, Long down, Long top);
}
