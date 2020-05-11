package pl.brzezinski.bookt.model;

import org.springframework.format.annotation.DateTimeFormat;
import pl.brzezinski.bookt.model.tables.ReservedTable;
import pl.brzezinski.bookt.validation.constraint.*;
import pl.brzezinski.bookt.validation.constraint.groupSequences.FirstValidation;
import pl.brzezinski.bookt.validation.constraint.groupSequences.SecondValidation;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static pl.brzezinski.bookt.validation.validator.lang.Lang.*;

@Entity
@OpenHoursForReservation(groups = SecondValidation.class, message = "{pl.brzezinski.bookt.Reservation.OpenHoursForReservation}")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long id;

    @Size(groups = FirstValidation.class, min = 2, max = 20, message = "{pl.brzezinski.bookt.Reservation.name.Size}")
    private String name;

    @Min(groups = FirstValidation.class, value = 1, message = "{pl.brzezinski.bookt.Reservation.numberOfPersons.Min}")
    private int numberOfPersons;

    @Phone(groups = FirstValidation.class, message = "{pl.brzezinski.bookt.Reservation.phoneNumber.Phone}")
    private String phoneNumber;

    @NotBadWord(groups = FirstValidation.class, lang = {PL, ENG}, message = "{pl.brzezinski.bookt.Reservation.notes.NotBadWord}")
    private String notes;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(groups = FirstValidation.class, message = "{pl.brzezinski.bookt.Reservation.dateTime.NotNull}")
    @Future(groups = FirstValidation.class, message = "{pl.brzezinski.bookt.Reservation.dateTime.Future}")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    @NotNull(groups = FirstValidation.class, message = "{pl.brzezinski.bookt.Reservation.restaurant.NotNull}")
    private Restaurant restaurant;

    @OneToOne
    @JoinColumn(name = "id_reserved_table")
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

