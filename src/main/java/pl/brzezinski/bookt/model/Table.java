package pl.brzezinski.bookt.model;

import org.springframework.format.annotation.DateTimeFormat;
import pl.brzezinski.bookt.model.enums.isTableOccupied;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_table")
    private Long id;
    private Long places;
    private isTableOccupied isOccupied;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateOfReservation;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne
    private Reservation reservation;

    public Table() {
    }

    public Table(Long places, LocalDateTime dateOfReservation){
        this.places = places;
        this.dateOfReservation = dateOfReservation;
    }

    public LocalDateTime getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(LocalDateTime dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
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

    @Override
    public String toString() {
        return "SingleTable{" +
                "id=" + id +
                ", places=" + places +
                ", isOccupied=" + isOccupied +
                '}';
    }
}
