package pl.brzezinski.bookt.model;

import pl.brzezinski.bookt.model.enums.Genre;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String street;
    private String city;
    private String postCode;
    private Genre genre;
    private String url;
    private String openTime;
    private String closeTime;
    private String phoneNumber;
    @OneToMany(mappedBy = "restaurant")
    private List<Table> tables = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<SchemaTable> schemaTables = new ArrayList<>();

    public void addTable(Table table){
        table.setRestaurant(this);
        getTables().add(table);
    }

    public Restaurant() {
    }

    public Restaurant(String name, String street, String city, String postCode, Genre genre, String url,
                      String openTime, String closeTime, String phoneNumber){
        this.name = name;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.genre = genre;
        this.url = url;
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

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
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

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
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
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", genre=" + genre +
                ", url='" + url + '\'' +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
