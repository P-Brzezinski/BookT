package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
import pl.brzezinski.bookt.model.users.User;
import pl.brzezinski.bookt.service.RestaurantMenuService;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class RestaurantController {

    private RestaurantService restaurantService;
    private RestaurantMenuService restaurantMenuService;
    private UserService userService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantMenuService restaurantMenuService, UserService userService) {
        this.restaurantService = restaurantService;
        this.restaurantMenuService = restaurantMenuService;
        this.userService = userService;
    }

    @GetMapping("/addRestaurant")
    public String addRestaurantForm(Restaurant restaurant, Model model){
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("genre", List.of(Genre.values()));
        return "addRestaurantForm";
    }

    @PostMapping("/save")
    public String saveRestaurant(@Valid Restaurant restaurant, BindingResult result, Principal principal, Model model){
        if (result.hasErrors()){
            System.out.println(result.toString());
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("genre", List.of(Genre.values()));
            return "addRestaurantForm";
        }
        User restaurantOwner = userService.findByEmail(principal.getName());
        restaurant.setRestaurantOwner(restaurantOwner);
        userService.add(restaurantOwner);
        restaurantService.add(restaurant);
        System.out.println(restaurantOwner.toString());
        System.out.println(restaurant.toString());
        return "redirect:/restaurateurPanel";
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
        RestaurantMenu restaurantMenu = restaurantMenuService.findByRestaurant(restaurant);
        List<Meal> meals = restaurantMenu.getMeals();
        model.addAttribute("meals", meals);
        model.addAttribute("restaurant", restaurant);
        return "showMenu";
    }
}
