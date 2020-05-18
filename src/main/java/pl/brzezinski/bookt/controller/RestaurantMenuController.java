package pl.brzezinski.bookt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.restaurantMenu.Meal;
import pl.brzezinski.bookt.model.restaurantMenu.RestaurantMenu;
import pl.brzezinski.bookt.service.MealService;
import pl.brzezinski.bookt.service.RestaurantMenuService;
import pl.brzezinski.bookt.service.RestaurantService;

import java.util.List;

@Controller
@SessionAttributes("restaurant")
@RequestMapping({"/restaurateurPanel", "/"})
public class RestaurantMenuController {

    private RestaurantService restaurantService;
    private RestaurantMenuService restaurantMenuService;
    private MealService mealService;

    public RestaurantMenuController(RestaurantService restaurantService, RestaurantMenuService restaurantMenuService, MealService mealService) {
        this.restaurantService = restaurantService;
        this.restaurantMenuService = restaurantMenuService;
        this.mealService = mealService;
    }

    @ModelAttribute
    public Restaurant restaurant(){
        return new Restaurant();
    }

    @GetMapping("/showMenu")
    public String showMenu(@ModelAttribute("restaurant") Restaurant restaurant, @RequestParam(required = false) Long restaurantId, Model model) {
        if (restaurant.getId() == null){
            restaurant = restaurantService.get(restaurantId);
            model.addAttribute("restaurant", restaurant);
        }
        RestaurantMenu restaurantMenu = restaurantMenuService.findByRestaurant(restaurant);
        List<Meal> meals = restaurantMenu.getMeals();
        model.addAttribute("meals", meals);
        model.addAttribute("restaurant", restaurant);
        return "showMenu";
    }

    @GetMapping("/mealForm")
    public String mealForm(@ModelAttribute("restaurant") Restaurant restaurant, @RequestParam(required = false) Long mealId, Model model){
        Meal meal;
        if (mealId != null){
            meal = mealService.get(mealId);
        }else {
            meal = new Meal();
        }
        meal.setRestaurantMenu(restaurant.getRestaurantMenu());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/saveMeal")
    public String saveMeal(@ModelAttribute Meal meal){
        mealService.add(meal);
        RestaurantMenu restaurantMenu = meal.getRestaurantMenu();
        restaurantMenuService.add(restaurantMenu);
        restaurantService.add(restaurantMenu.getRestaurant());
        return "redirect:/restaurateurPanel/showAllRestaurants";
    }

    @GetMapping("/deleteMeal")
    public String deleteMeal(@RequestParam Long mealId){
        mealService.deleteById(mealId);
        return "redirect:/showMenu";
    }
}
