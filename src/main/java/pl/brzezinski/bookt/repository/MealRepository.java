package pl.brzezinski.bookt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.bookt.model.restaurantMenu.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
}
