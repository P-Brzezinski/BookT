package pl.brzezinski.bookt.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private Integer number;
    private String city;
    private String postCode;
    @OneToOne
    private Restaurant restaurant;

    public Address(){}

    public Address(String street, Integer number, String city, String postCode,Restaurant restaurant) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.postCode = postCode;
        this.restaurant = restaurant;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", restaurant=" + restaurant +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(street, address.street) &&
                Objects.equals(number, address.number) &&
                Objects.equals(city, address.city) &&
                Objects.equals(postCode, address.postCode) &&
                Objects.equals(restaurant, address.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, number, city, postCode, restaurant);
    }
}
