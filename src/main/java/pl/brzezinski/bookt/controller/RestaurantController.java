package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.restaurantMenu.Meal;
import pl.brzezinski.bookt.model.restaurantMenu.RestaurantMenu;
import pl.brzezinski.bookt.service.RestaurantMenuService;
import pl.brzezinski.bookt.service.RestaurantService;

import java.util.List;

@Controller
public class RestaurantController {

    private RestaurantService restaurantService;
    private RestaurantMenuService restaurantMenuService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantMenuService restaurantMenuService) {
        this.restaurantService = restaurantService;
        this.restaurantMenuService = restaurantMenuService;
    }

    @GetMapping("/showAllRestaurants")
    public String showAllRestaurants(Model model) {
        List<Restaurant> allRestaurants = restaurantService.getAll();
        model.addAttribute("allRestaurants", allRestaurants);
        return "showAllRestaurants";
    }

    @GetMapping("/showMenu")
    public String showMenu(@RequestParam Long restaurantId, Model model) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        RestaurantMenu restaurantMenu = restaurantMenuService.findByRestaurant(restaurant);
        List<Meal> meals = restaurantMenu.getMeals();
        model.addAttribute("meals", meals);
        model.addAttribute("restaurant", restaurant);
        return "showMenu";
    }
}
