package pl.brzezinski.bookt.model.restaurantMenu;

import pl.brzezinski.bookt.model.Restaurant;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant_menus")
public class RestaurantMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurant_menu")
    private Long id;
    @OneToOne
    @JoinColumn(name = "resaurant_id")
    private Restaurant restaurant;
    @OneToMany(mappedBy = "restaurantMenu")
    private List<Meal> meals = new ArrayList<>();

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

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
