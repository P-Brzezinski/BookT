package pl.brzezinski.bookt.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.model.restaurantMenu.Meal;
import pl.brzezinski.bookt.model.restaurantMenu.RestaurantMenu;
import pl.brzezinski.bookt.model.users.User;
import pl.brzezinski.bookt.repository.MealRepository;
import pl.brzezinski.bookt.service.MealService;
import pl.brzezinski.bookt.service.RestaurantMenuService;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class RestaurateurController {

    private RestaurantService restaurantService;
    private UserService userService;

    public RestaurateurController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @RequestMapping("/restaurateurPanel")
    public String restaurateur(@RequestParam(required = false) Long restaurantId, Principal principal, Model model){
        model.addAttribute("principal", principal);
        return "restaurateurPanel";}

    @GetMapping("/restaurateurPanel/addRestaurant")
    public String addRestaurantForm(Restaurant restaurant, Model model){
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("genre", List.of(Genre.values()));
        return "addRestaurantForm";
    }

    @PostMapping("/restaurateurPanel/save")
    public String saveRestaurant(@Valid Restaurant restaurant, BindingResult result, Principal principal, Model model){
        if (result.hasErrors()){
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("genre", List.of(Genre.values()));
            return "addRestaurantForm";
        }
        User restaurantOwner = userService.findByEmail(principal.getName());
        restaurant.setRestaurantOwner(restaurantOwner);
        userService.add(restaurantOwner);
        restaurantService.add(restaurant);
        System.out.println(restaurant.toString());
        return "redirect:/restaurateurPanel";
    }

    @GetMapping("/restaurateurPanel/showAllRestaurants")
    public String showRestaurants(Principal principal, Model model){
        User restaurantOwner = userService.findByEmail(principal.getName());
        List<Restaurant> allRestaurants = restaurantOwner.getRestaurants();
        model.addAttribute("allRestaurants", allRestaurants);
        return "showAllRestaurants";
    }
}
