package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.service.ReservationService;
import pl.brzezinski.bookt.service.RestaurantService;

import java.util.List;

import static pl.brzezinski.bookt.service.RestaurantService.*;

@Controller
public class ReservationController {

    private RestaurantService restaurantService;
    private ReservationService reservationService;

    @Autowired
    public ReservationController(RestaurantService restaurantService, ReservationService reservationService) {
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
    }

    @GetMapping("/askForReservation")
    public String askForReservation(Model model) {
        List<Restaurant> allRestaurants = restaurantService.getAll();
        model.addAttribute("allRestaurants", allRestaurants);
        model.addAttribute("newReservation", new Reservation());
        return "askForReservation";
    }

    @PostMapping("/checkIfPossible")
    public String checkIfPossible(@ModelAttribute Reservation newReservation, Model model) {
        String isPossible = reservationService.isPossible(newReservation);
        model.addAttribute("newReservation", newReservation);
        System.out.println(isPossible);

//        switch (isPossible) {
//            case RESERVATION_AVAILABLE:
//                return "success";
//            case ALL_TABLES_ARE_OCCUPIED:
//                return "findAnotherTable";
//            case NO_TABLE_AVAILABLE_IN_RESTAURANT:
//                return "findAnotherRestaurant";
//        }
        return "redirect:/";
    }
}
