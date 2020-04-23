package pl.brzezinski.bookt.model;

import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long id;
    private String name;
    private Long numberOfPersons;
    private Long phoneNumber;
    private String notes;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;
    @OneToOne
    @JoinColumn(name = "id_single_table")
    private SingleTable singleTable;

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

    public Long getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(Long numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
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

    public SingleTable getSingleTable() {
        return singleTable;
    }

    public void setSingleTable(SingleTable singleTable) {
        this.singleTable = singleTable;
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

