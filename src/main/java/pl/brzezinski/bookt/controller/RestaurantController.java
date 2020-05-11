package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.service.RestaurantService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
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
}
