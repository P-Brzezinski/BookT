package pl.brzezinski.bookt.model.tables;

import org.springframework.format.annotation.DateTimeFormat;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reserved_table")
public class ReservedTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int tableNumber;
    private int places;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateOfReservation;
    @ManyToOne
    private Restaurant restaurant;

    @OneToOne(mappedBy = "reservedTable")
    private Reservation reservation;

    public ReservedTable() {
    }

    public ReservedTable(int tableNumber, int places, LocalDateTime dateOfReservation){
        this.tableNumber = tableNumber;
        this.places = places;
        this.dateOfReservation = dateOfReservation;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
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

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "ReservedTable{" +
                "id=" + id +
                ", dateOfReservation=" + dateOfReservation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservedTable table = (ReservedTable) o;
        return Objects.equals(places, table.places) &&
                Objects.equals(tableNumber, table.tableNumber) &&
                Objects.equals(dateOfReservation, table.dateOfReservation) &&
                Objects.equals(restaurant, table.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(places, tableNumber, dateOfReservation, restaurant);
    }


}
