package pl.brzezinski.bookt.model.restaurantMenu;

import javax.persistence.*;

@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    @ManyToOne
    private RestaurantMenu restaurantMenu;
}
