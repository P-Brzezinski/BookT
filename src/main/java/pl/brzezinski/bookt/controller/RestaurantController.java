package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.model.restaurantMenu.Meal;
import pl.brzezinski.bookt.model.restaurantMenu.RestaurantMenu;
import pl.brzezinski.bookt.service.RestaurantMenuService;
import pl.brzezinski.bookt.service.RestaurantService;

import javax.validation.Valid;
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

    @GetMapping("/addRestaurant")
    public String addRestaurantForm(Model model){
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("genre", List.of(Genre.values()));
        return "addRestaurantForm";
    }

    @PostMapping("/save")
    public String saveRestaurant(@Valid Restaurant restaurant, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("genre", List.of(Genre.values()));
            return "addRestaurantForm";
        }
        restaurantService.add(restaurant);
        return "redirect:/";
    }

    @GetMapping("/showAllRestaurants")
    public String showAllRestaurants(Model model){
        List<Restaurant> allRestaurants = restaurantService.getAll();
        model.addAttribute("allRestaurants", allRestaurants);
        return "showAllRestaurants";
    }

    @GetMapping("/showMenu")
    public String showMenu(@RequestParam Long restaurantId, Model model){
        Restaurant restaurant = restaurantService.get(restaurantId);
        System.out.println(restaurant);
        RestaurantMenu restaurantMenu = restaurantMenuService.findByRestaurant(restaurant);
        System.out.println(restaurantMenu);
        List<Meal> meals = restaurantMenu.getMeals();
        System.out.println(meals);
        model.addAttribute("meals", meals);
        model.addAttribute("restaurant", restaurant);
        return "showMenu";
    }
}
