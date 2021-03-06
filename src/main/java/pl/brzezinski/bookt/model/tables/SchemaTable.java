package pl.brzezinski.bookt.model.tables;

import pl.brzezinski.bookt.model.Restaurant;

import javax.persistence.*;

@Entity
@Table(name = "schema_tables")
public class SchemaTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_schema_table")
    private Long id;
    private int tableNumber;
    private int places;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public SchemaTable() {
    }

    public SchemaTable(int tableNumber, int places){
        this.tableNumber = tableNumber;
        this.places = places;

    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int number) {
        this.tableNumber = number;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "SchemaTable{" +
                "id=" + id;
    }
}
