package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.SchemaTableService;

import java.util.List;

@Controller
public class RestaurantController {

    private RestaurantService restaurantService;
    private SchemaTableService schemaTableService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, SchemaTableService schemaTableService) {
        this.restaurantService = restaurantService;
        this.schemaTableService = schemaTableService;
    }

    @GetMapping("/addRestaurant")
    public String addRestaurantForm(Model model){
        model.addAttribute("newRestaurant", new Restaurant());
        model.addAttribute("genres", List.of(Genre.values()));
        return "addRestaurantForm";
    }

    @PostMapping("/save")
    public String saveRestaurant(@ModelAttribute Restaurant newRestaurant){
        restaurantService.save(newRestaurant);
        return "redirect:/";
    }

    @GetMapping("/showAllRestaurants")
    public String showAllRestaurants(Model model){
        List<Restaurant> allRestaurants = restaurantService.getAll();
        model.addAttribute("allRestaurants", allRestaurants);
        return "showAllRestaurants";
    }
}
