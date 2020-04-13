package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SingleTable;
import pl.brzezinski.bookt.repository.ReservationRepository;
import pl.brzezinski.bookt.repository.RestaurantRepository;

import java.util.List;

@Controller
public class TableController {

    private RestaurantRepository restaurantDAO;

    @Autowired
    public TableController(RestaurantRepository restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    @GetMapping("/showAllTablesInRestaurant")
    public String showAllTablesInRestaurant(@RequestParam Long restaurantId, Model model){
        Restaurant findRestaurant = restaurantDAO.getOne(restaurantId);
        List<SingleTable> allTablesInRestaurants = findRestaurant.getTables();
        model.addAttribute("allTablesInRestaurant", allTablesInRestaurants);
        model.addAttribute("restaurantId", restaurantId);
        return "showAllTablesInRestaurant";
    }
}
