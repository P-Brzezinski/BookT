package pl.brzezinski.bookt.model;

import javax.persistence.*;

@Entity
public class SchemaTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tableNumber;
    private Long places;
    @ManyToOne
    private Restaurant restaurant;

    public SchemaTable() {
    }

    public SchemaTable(Long tableNumber, Long places){
        this.tableNumber = tableNumber;
        this.places = places;

    }

    public Long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Long number) {
        this.tableNumber = number;
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
                ", tableNumber=" + tableNumber +
                ", places=" + places +
                ", restaurant=" + restaurant.getName() +
                '}';
    }
}
