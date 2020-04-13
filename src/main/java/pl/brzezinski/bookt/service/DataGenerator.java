package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SingleTable;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.repository.RestaurantRepository;
import pl.brzezinski.bookt.repository.TableRepository;

import javax.annotation.PostConstruct;

import java.time.LocalDateTime;

@Service
public class DataGenerator {

    private RestaurantRepository restaurantDAO;
    private TableRepository tableDAO;

    @Autowired
    public DataGenerator(RestaurantRepository restaurantDAO, TableRepository tableDAO) {
        this.restaurantDAO = restaurantDAO;
        this.tableDAO = tableDAO;
    }

    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private Restaurant restaurant3;
    private Restaurant restaurant4;

    private SingleTable table1 = new SingleTable(2l, LocalDateTime.of(2020,12,12,12,12));
    private SingleTable table2 = new SingleTable(2l, LocalDateTime.of(2020,12,12,12,12));
    private SingleTable table3 = new SingleTable(4l, LocalDateTime.of(2020,12,12,12,12));
    private SingleTable table4 = new SingleTable(4l, LocalDateTime.of(2020,12,15,12,12));
    private SingleTable table5 = new SingleTable(6l, LocalDateTime.of(2020,12,16,12,12));
    private SingleTable table6 = new SingleTable(8l, LocalDateTime.of(2020,12,17,12,12));

    @PostConstruct
    public void createRestaurantData(){
        restaurantDAO.save(restaurant1 = new Restaurant("Pod Fredra", "Rynek Ratusz 1", "Wroclaw", "54-900", Genre.POLISH, "www.podfredra.pl", "10:00", "24:00", "899-998-323"));
        restaurantDAO.save(restaurant2 = new Restaurant("Cesarsko Królewska", "Rynek 12", "Wroclaw", "32-999", Genre.POLISH, "www.ck.pl", "12:00", "02:00", "111-222-333"));
        restaurantDAO.save(restaurant3 = new Restaurant("La Scala", "Rynek 38", "Wroclaw", "50-102", Genre.ITALIAN, "www.lascala.pl", "12:00", "23:00", "71-372-53-94"));
        restaurantDAO.save(restaurant4 = new Restaurant("Akropolis", "Rynek 16/17", "Wroclaw", "50-101", Genre.GREEK, "www.akropolis.wroc.pl", "10:00", "23:00", "71-343-14-13"));
    }

    @PostConstruct
    public void createTableData(){
        table1.setRestaurant(restaurant1);
        table2.setRestaurant(restaurant1);
        table3.setRestaurant(restaurant1);
        table4.setRestaurant(restaurant1);
        table5.setRestaurant(restaurant1);
        table6.setRestaurant(restaurant1);
        tableDAO.save(table1);
        tableDAO.save(table2);
        tableDAO.save(table3);
        tableDAO.save(table4);
        tableDAO.save(table5);
        tableDAO.save(table6);

//        restaurant1.addTable(table1);
//        tableDAO.save(table2);
//        tableDAO.save(table3);
//        tableDAO.save(table4);
//        tableDAO.save(table5);
//        tableDAO.save(table6);
    }
//
//    @PostConstruct
//    public void addTablesToRestaurant(){
//        restaurant1.addTable(table1);
//        restaurant1.addTable(table2);
//        restaurant1.addTable(table3);
//        restaurant1.addTable(table4);
//        restaurant1.addTable(table5);
//        restaurant1.addTable(table6);
//        restaurantDAO.save(restaurant1);
//    }
}
