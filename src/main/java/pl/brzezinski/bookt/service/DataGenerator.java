package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.repository.RestaurantRepository;
import pl.brzezinski.bookt.repository.SchemaTableRepository;
import pl.brzezinski.bookt.repository.ReservedTableRepository;

import javax.annotation.PostConstruct;
import java.time.LocalTime;

@Service
public class DataGenerator {

    private RestaurantRepository restaurantDAO;
    private ReservedTableRepository tableDAO;
    private SchemaTableRepository schemaTableRepository;

    @Autowired
    public DataGenerator(RestaurantRepository restaurantDAO, ReservedTableRepository tableDAO, SchemaTableRepository schemaTableRepository) {
        this.restaurantDAO = restaurantDAO;
        this.tableDAO = tableDAO;
        this.schemaTableRepository = schemaTableRepository;
    }

    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private Restaurant restaurant3;
    private Restaurant restaurant4;

    @PostConstruct
    public void createRestaurantData(){
        restaurantDAO.save(restaurant1 = new Restaurant("Pod Fredra", "Rynek Ratusz 1", "Wroclaw", "54-900", Genre.POLISH, "http://www.podfredra.pl", "restauracja@podfreda.pl", LocalTime.of(10,0), LocalTime.of(23,0), "899-998-323"));
        restaurantDAO.save(restaurant2 = new Restaurant("Cesarsko Królewska", "Rynek 12", "Wroclaw", "32-999", Genre.POLISH, "http://www.ck.pl", "restauracja@ck.pl",LocalTime.of(12,0),LocalTime.of(23,0), "111-222-333"));
        restaurantDAO.save(restaurant3 = new Restaurant("La Scala", "Rynek 38", "Wroclaw", "50-102", Genre.ITALIAN, "http://www.lascala.pl", "restauracja@lascala.pl", LocalTime.of(12, 0),LocalTime.of(23,0), "71-372-53-94"));
        restaurantDAO.save(restaurant4 = new Restaurant("Akropolis", "Rynek 16/17", "Wroclaw", "50-101", Genre.GREEK, "http://www.akropolis.wroc.pl", "restauracja@akropolis.pl", LocalTime.of(10,0),LocalTime.of(23,0), "71-343-14-13"));
    }

    @PostConstruct
    public void addSchemaTables(){
        SchemaTable schemaTable1 = new SchemaTable(1, 2 );
        SchemaTable schemaTable2 = new SchemaTable(2, 2 );
        SchemaTable schemaTable3 = new SchemaTable(3, 2 );
        SchemaTable schemaTable4 = new SchemaTable(4, 2 );
        SchemaTable schemaTable5 = new SchemaTable(5, 4 );
        SchemaTable schemaTable6 = new SchemaTable(6, 6 );
        schemaTable1.setRestaurant(restaurant1);
        schemaTable2.setRestaurant(restaurant1);
        schemaTable3.setRestaurant(restaurant1);
        schemaTable4.setRestaurant(restaurant1);
        schemaTable5.setRestaurant(restaurant1);
        schemaTable6.setRestaurant(restaurant1);
        schemaTableRepository.save(schemaTable1);
        schemaTableRepository.save(schemaTable2);
        schemaTableRepository.save(schemaTable3);
        schemaTableRepository.save(schemaTable4);
        schemaTableRepository.save(schemaTable5);
        schemaTableRepository.save(schemaTable6);

    }
}
