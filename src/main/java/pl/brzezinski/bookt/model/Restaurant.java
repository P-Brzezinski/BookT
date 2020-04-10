package pl.brzezinski.bookt.model;

import pl.brzezinski.bookt.model.enums.genre.Genre;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private Address address;
    private Genre genre;
    private String url;
    private Time openTime;
    private Time closeTime;
    private String phoneNumber;

    public Restaurant() {
    }

    public Restaurant(String name, Address address, Genre genre, String url, Time openTime, Time closeTime, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.genre = genre;
        this.url = url;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", genre=" + genre +
                ", url='" + url + '\'' +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                genre == that.genre &&
                Objects.equals(url, that.url) &&
                Objects.equals(openTime, that.openTime) &&
                Objects.equals(closeTime, that.closeTime) &&
                Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, genre, url, openTime, closeTime, phoneNumber);
    }
}
