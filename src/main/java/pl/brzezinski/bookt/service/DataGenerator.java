package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.enums.genre.Genre;
import pl.brzezinski.bookt.repository.RestaurantRepository;

import javax.annotation.PostConstruct;

@Service
public class DataGenerator {

    private RestaurantRepository restaurantDAO;

    @Autowired
    public DataGenerator(RestaurantRepository restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    @PostConstruct
    public void createRestaurantData(){
        restaurantDAO.save(new Restaurant("Pod Fredra", "Rynek Ratusz 1", "Wroclaw", "54-900", Genre.POLISH, "www.podfredra.pl", "10:00", "24:00", "899-998-323"));
        restaurantDAO.save(new Restaurant("Cesarsko Królewska", "Rynek 12", "Wroclaw", "32-999", Genre.POLISH, "www.ck.pl", "12:00", "02:00", "111-222-333"));
        restaurantDAO.save(new Restaurant("La Scala", "Rynek 38", "Wroclaw", "50-102", Genre.ITALIAN, "www.lascala.pl", "12:00", "23:00", "71-372-53-94"));
        restaurantDAO.save(new Restaurant("Akropolis", "Rynek 16/17", "Wroclaw", "50-101", Genre.GREEK, "www.akropolis.wroc.pl", "10:00", "23:00", "71-343-14-13"));
    }
}
