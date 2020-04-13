package pl.brzezinski.bookt.model;

import pl.brzezinski.bookt.model.enums.isTableOccupied;

import javax.persistence.*;

@Entity
public class SingleTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_table")
    private Long id;
    private Long places;
    private isTableOccupied isOccupied;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public SingleTable() {
    }

    public SingleTable(Long places, isTableOccupied isOccupied){
        this.places = places;
        this.isOccupied = isOccupied;
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

    public isTableOccupied getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(isTableOccupied isOccupied) {
        this.isOccupied = isOccupied;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
