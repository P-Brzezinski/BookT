package pl.brzezinski.bookt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.restaurantMenu.RestaurantMenu;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Long> {

    RestaurantMenu findByRestaurant(Restaurant restaurant);
}
