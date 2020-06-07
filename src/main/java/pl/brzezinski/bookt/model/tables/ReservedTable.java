package pl.brzezinski.bookt.model.tables;

import org.springframework.format.annotation.DateTimeFormat;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reserved_tables")
public class ReservedTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserved_table")
    private Long id;
    private int tableNumber;
    private int places;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateOfReservation;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(mappedBy = "reservedTable")
    private Reservation reservation;

    public ReservedTable() {
    }

    public ReservedTable(int tableNumber, int places, LocalDateTime dateOfReservation) {
        this.tableNumber = tableNumber;
        this.places = places;
        this.dateOfReservation = dateOfReservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public LocalDateTime getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(LocalDateTime dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "ReservedTable{" +
                "id=" + id +
                ", tableNumber=" + tableNumber +
                ", places=" + places +
                ", dateOfReservation=" + dateOfReservation +
                ", restaurant=" + restaurant.getName() +
                ", reservation=" + reservation.getDateTime() + " for " + reservation.getName() +
                '}';
    }
}
