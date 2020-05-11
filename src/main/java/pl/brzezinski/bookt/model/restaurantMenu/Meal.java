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

    public Meal() {
    }

    public Meal(String name, double price){
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RestaurantMenu getRestaurantMenu() {
        return restaurantMenu;
    }

    public void setRestaurantMenu(RestaurantMenu restaurantMenu) {
        this.restaurantMenu = restaurantMenu;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", restaurantMenu=" + restaurantMenu +
                '}';
    }
}
