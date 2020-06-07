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

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping({"/restaurateurPanel", "/", "/admin"})
public class RestaurantMenuController {

    private RestaurantService restaurantService;
    private RestaurantMenuService restaurantMenuService;
    private MealService mealService;

    public RestaurantMenuController(RestaurantService restaurantService, RestaurantMenuService restaurantMenuService, MealService mealService) {
        this.restaurantService = restaurantService;
        this.restaurantMenuService = restaurantMenuService;
        this.mealService = mealService;
    }

    @GetMapping("/showMenu")
    public String showMenu(@RequestParam(required = false) Long restaurantId, Model model) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        RestaurantMenu restaurantMenu = restaurant.getRestaurantMenu();
        List<Meal> meals;
        if (restaurantMenu == null) {
            meals = Collections.emptyList();
        } else {
            meals = restaurantMenu.getMeals();
        }
        model.addAttribute("meals", meals);
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("restaurantName", restaurant.getName());
        return "showMenu";
    }

    @GetMapping("/mealForm")
    public String mealForm(@RequestParam Long restaurantId, @RequestParam(required = false) Long mealId, Model model) {
        Meal meal;
        if (mealId != null) {
            meal = mealService.get(mealId);
        } else {
            meal = new Meal();
        }
        model.addAttribute("meal", meal);
        model.addAttribute("restaurantId", restaurantId);
        return "mealForm";
    }

    @PostMapping("/saveMeal")
    public String saveMeal(@RequestParam Long restaurantId, @ModelAttribute Meal meal) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        RestaurantMenu restaurantMenu = restaurant.getRestaurantMenu();
        if (restaurantMenu == null){
            restaurantMenu = new RestaurantMenu();
            restaurantMenu.setRestaurant(restaurant);
        }
        meal.setRestaurantMenu(restaurantMenu);
        restaurantMenuService.add(restaurantMenu);
        mealService.add(meal);
        restaurant.setRestaurantMenu(restaurantMenu);
        restaurantService.add(restaurant);
        return "redirect:/showAllRestaurants";
    }

    @GetMapping("/deleteMeal")
    public String deleteMeal(@RequestParam Long mealId) {
        mealService.deleteById(mealId);
        return "redirect:/showAllRestaurants";
    }
}
