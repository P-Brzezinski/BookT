package pl.brzezinski.bookt.model;

import org.hibernate.validator.constraints.URL;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.model.restaurantMenu.RestaurantMenu;
import pl.brzezinski.bookt.model.tables.ReservedTable;
import pl.brzezinski.bookt.model.tables.SchemaTable;
import pl.brzezinski.bookt.model.users.User;
import pl.brzezinski.bookt.validation.constraint.OpenHoursForRestaurant;
import pl.brzezinski.bookt.validation.constraint.Phone;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@OpenHoursForRestaurant(message = "{pl.brzezinski.bookt.Restaurant.OpenHoursForRestaurant}")
@Table(name = "restaurants")
public class Restaurant {

    public static int ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_MINUTES = 180;
    public static int ESTIMATED_TIME_BETWEEN_RESERVATIONS_IN_MINUTES = 15;
    public static int ESTIMATED_MINIMUM_TIME_FOR_ONE_RESERVATION_IN_MINUTES = 60;
    public static int TABLE_WITH_MINIMUM_PLACES = 0;
    public static int TABLE_WITH_MAX_PLACES = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{pl.brzezinski.bookt.Restaurant.name.NotBlank}")
    private String name;
    @NotBlank(message = "{pl.brzezinski.bookt.Restaurant.street.NotBlank}")
    private String street;
    @NotBlank(message = "{pl.brzezinski.bookt.Restaurant.city.NotBlank}")
    private String city;
    @NotBlank(message = "{pl.brzezinski.bookt.Restaurant.postCode.NotBlank}")
    private String postCode;
    @NotNull(message = "{pl.brzezinski.bookt.Restaurant.genre.NotNull}")
    private Genre genre;
    @URL(message = "{pl.brzezinski.bookt.Restaurant.url.URL}")
    @NotBlank(message = "{pl.brzezinski.bookt.Restaurant.url.NotBlank}")
    private String url;
    @Email(message = "{pl.brzezinski.bookt.Restaurant.email.Email}")
    @NotBlank(message = "{pl.brzezinski.bookt.Restaurant.email.NotBlank}")
    private String email;
    @Basic
    private LocalTime openTime;
    @Basic
    private LocalTime closeTime;
    @Phone(message = "{pl.brzezinski.bookt.Restaurant.phoneNumber.Phone}")
    private String phoneNumber;
    @OneToMany(mappedBy = "restaurant",
    cascade = CascadeType.PERSIST)
    private List<ReservedTable> reservedTables = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant",
    cascade = CascadeType.PERSIST)
    private List<SchemaTable> schemaTables = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant",
    cascade = CascadeType.PERSIST)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToOne(mappedBy = "restaurant",
    cascade = CascadeType.PERSIST)
    private RestaurantMenu restaurantMenu;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User restaurantOwner;

    public Restaurant() {
    }

    public Restaurant(String name, String street, String city, String postCode, Genre genre, String url, String email,
                      LocalTime openTime, LocalTime closeTime, String phoneNumber){
        this.name = name;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.genre = genre;
        this.url = url;
        this.email = email;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.phoneNumber = phoneNumber;
    }

    public List<SchemaTable> getSchemaTables() {
        return schemaTables;
    }

    public void setSchemaTables(List<SchemaTable> schemaTables) {
        this.schemaTables = schemaTables;
    }

    public List<ReservedTable> getReservedTables() {
        return reservedTables;
    }

    public void setReservedTables(List<ReservedTable> reservedTables) {
        this.reservedTables = reservedTables;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public RestaurantMenu getRestaurantMenu() {
        return restaurantMenu;
    }

    public void setRestaurantMenu(RestaurantMenu restaurantMenu) {
        this.restaurantMenu = restaurantMenu;
    }

    public User getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(User restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", genre=" + genre +
                ", url='" + url + '\'' +
                ", email='" + email + '\'' +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", restaurantOwner=" + restaurantOwner.getEmail() +
                '}';
    }
}
