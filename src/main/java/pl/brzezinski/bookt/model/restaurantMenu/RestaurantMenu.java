package pl.brzezinski.bookt.model.restaurantMenu;

import pl.brzezinski.bookt.model.Restaurant;

import javax.persistence.*;
import java.util.List;

@Entity
public class RestaurantMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Restaurant restaurant;
    @OneToMany(mappedBy = "restaurantMenu")
    private List<Meal> meal;

    public RestaurantMenu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Meal> getMeal() {
        return meal;
    }

    public void setMeal(List<Meal> meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "RestaurantMenu{" +
                "id=" + id +
                ", restaurant=" + restaurant +
                '}';
    }
}
