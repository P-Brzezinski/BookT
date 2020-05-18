package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.model.users.User;
import pl.brzezinski.bookt.repository.RoleRepository;
import pl.brzezinski.bookt.service.RestaurantMenuService;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping({"/", "/restaurateurPanel"})
public class RestaurantController {

    private RestaurantService restaurantService;
    private RestaurantMenuService restaurantMenuService;
    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantMenuService restaurantMenuService, UserService userService, RoleRepository roleRepository) {
        this.restaurantService = restaurantService;
        this.restaurantMenuService = restaurantMenuService;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/showAllRestaurants")
    public String showAllRestaurants(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        List<Restaurant> allRestaurants;
        if (user.getRoles().contains(roleRepository.findByName("ROLE_RESTAURATEUR"))){
            allRestaurants = user.getRestaurants();
        }else {
            allRestaurants = restaurantService.getAll();
        }
        model.addAttribute("allRestaurants", allRestaurants);
        return "showAllRestaurants";
    }

    @GetMapping("/addRestaurant")
    public String addRestaurantForm(Restaurant restaurant, Model model){
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("genre", List.of(Genre.values()));
        return "addRestaurantForm";
    }

    @PostMapping("/saveRestaurant")
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
        return "redirect:/restaurateurPanel";
    }
}
