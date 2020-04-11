package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.Table;
import pl.brzezinski.bookt.model.enums.genre.Genre;
import pl.brzezinski.bookt.repository.RestaurantRepository;
import pl.brzezinski.bookt.repository.TableRepository;

import javax.annotation.PostConstruct;
import java.util.List;

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

    @PostConstruct
    public void createRestaurantData(){
        restaurantDAO.save(restaurant1 = new Restaurant("Pod Fredra", "Rynek Ratusz 1", "Wroclaw", "54-900", Genre.POLISH, "www.podfredra.pl", "10:00", "24:00", "899-998-323"));
        restaurantDAO.save(restaurant2 = new Restaurant("Cesarsko Królewska", "Rynek 12", "Wroclaw", "32-999", Genre.POLISH, "www.ck.pl", "12:00", "02:00", "111-222-333"));
        restaurantDAO.save(restaurant3 = new Restaurant("La Scala", "Rynek 38", "Wroclaw", "50-102", Genre.ITALIAN, "www.lascala.pl", "12:00", "23:00", "71-372-53-94"));
        restaurantDAO.save(restaurant4 = new Restaurant("Akropolis", "Rynek 16/17", "Wroclaw", "50-101", Genre.GREEK, "www.akropolis.wroc.pl", "10:00", "23:00", "71-343-14-13"));
    }

    @PostConstruct
    public void createTableData(){
        List<Table> tablesInRestaurant1 = restaurant1.getTables();
        tablesInRestaurant1.add(new Table(2l, false));
        tablesInRestaurant1.add(new Table(2l, false));
        tablesInRestaurant1.add(new Table(4l, false));
        tablesInRestaurant1.add(new Table(4l, false));
        tablesInRestaurant1.add(new Table(6l, false));
        tablesInRestaurant1.add(new Table(8l, false));
    }
}
