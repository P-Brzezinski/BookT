package pl.brzezinski.bookt.model;

import org.springframework.format.annotation.DateTimeFormat;
import pl.brzezinski.bookt.validation.constraint.NotBadWord;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static pl.brzezinski.bookt.validation.validator.lang.Lang.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long id;

    @Size(min = 2, max = 20, message = "{pl.brzezinski.bookt.Reservation.name.Size}")
    private String name;

    @Min(value = 1, message = "{pl.brzezinski.bookt.Reservation.numberOfPersons.Min}")
    private int numberOfPersons;

    @Size(min = 13, message = "{pl.brzezinski.bookt.Reservation.phoneNumber.Digits}")
    private String phoneNumber;

    @NotBadWord(lang = {PL, ENG}, message = "{pl.brzezinski.bookt.Reservation.notes.NotBadWord}")
    private String notes;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "{pl.brzezinski.bookt.Reservation.dateTime.NotNull}")
    @Future(message = "{pl.brzezinski.bookt.Reservation.dateTime.Future}")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    @NotNull(message = "{pl.brzezinski.bookt.Reservation.restaurant.NotNul;l}")
    private Restaurant restaurant;

    @OneToOne
    @JoinColumn(name = "id_single_table")
    private ReservedTable reservedTable;

    public Reservation() {
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public ReservedTable getReservedTable() {
        return reservedTable;
    }

    public void setReservedTable(ReservedTable reservedTable) {
        this.reservedTable = reservedTable;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfPersons=" + numberOfPersons +
                ", phoneNumber=" + phoneNumber +
                ", notes='" + notes + '\'' +
                ", dateTime=" + dateTime +
                ", restaurant=" + restaurant.getName() +
                '}';
    }
}

