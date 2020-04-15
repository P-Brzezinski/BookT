package pl.brzezinski.bookt.model;

import javax.persistence.*;

@Entity
public class SchemaTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private Long places;
    @ManyToOne
    private Restaurant restaurant;

    public SchemaTable() {
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlaces() {
        return places;
    }

    public void setPlaces(Long places) {
        this.places = places;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "SchemaTable{" +
                "id=" + id +
                ", places=" + places +
                ", restaurant=" + restaurant +
                '}';
    }
}
