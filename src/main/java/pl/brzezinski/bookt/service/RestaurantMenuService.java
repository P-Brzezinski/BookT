package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.restaurantMenu.RestaurantMenu;
import pl.brzezinski.bookt.repository.RestaurantMenuRepository;

import java.util.List;

@Service
public class RestaurantMenuService implements GenericService<Long, RestaurantMenu> {

    private RestaurantMenuRepository restaurantMenuRepository;

    @Autowired
    public RestaurantMenuService(RestaurantMenuRepository restaurantMenuRepository) {
        this.restaurantMenuRepository = restaurantMenuRepository;
    }

    @Override
    public RestaurantMenu get(Long id) {
        return restaurantMenuRepository.getOne(id);
    }

    @Override
    public void add(RestaurantMenu restaurantMenu) {
        restaurantMenuRepository.save(restaurantMenu);
    }

    @Override
    public void remove(RestaurantMenu restaurantMenu) {
        restaurantMenuRepository.delete(restaurantMenu);
    }

    @Override
    public List<RestaurantMenu> getAll() {
        return restaurantMenuRepository.findAll();
    }

    public RestaurantMenu findByRestaurant(Restaurant restaurant){
        return restaurantMenuRepository.findByRestaurant(restaurant);
    }
}
