package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SingleTable;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.repository.RestaurantRepository;

import java.util.List;

@Controller
public class RestaurantController {

    private RestaurantRepository restaurantDAO;

    @Autowired
    public RestaurantController(RestaurantRepository restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    @GetMapping("/add")
    public String viewNameForm(Model model){
        model.addAttribute("newRestaurant", new Restaurant());
        model.addAttribute("genres", List.of(Genre.values()));
        return "addForm";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Restaurant newRestaurant){
        restaurantDAO.save(newRestaurant);
        return "redirect:/";
    }

    @GetMapping("/showAllRestaurants")
    public String showAllRestaurants(Model model){
        List<Restaurant> allRestaurants = restaurantDAO.findAll();
        model.addAttribute("allRestaurants", allRestaurants);
        return "showAllRestaurants";
    }

    @GetMapping("/showAllTablesInRestaurant")
    public String showAllTablesInRestaurant(@RequestParam Long restaurantId, Model model){
        Restaurant findRestaurant = restaurantDAO.getOne(restaurantId);
        List<SingleTable> allTablesInRestaurants = findRestaurant.getTables();
        model.addAttribute("allTablesInRestaurant", allTablesInRestaurants);
        return "showAllTablesInRestaurant";
    }
}
