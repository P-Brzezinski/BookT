package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.repository.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantService implements GenericService<Long, Restaurant> {

    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant get(Long id) {
        return restaurantRepository.getOne(id);
    }

    @Override
    public void add(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    @Override
    public void remove(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}

