package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.service.ReservationService;
import pl.brzezinski.bookt.service.RestaurantService;

import javax.validation.Valid;
import java.util.List;

import static pl.brzezinski.bookt.service.ReservationService.*;

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
        model.addAttribute("reservation", new Reservation());
        return "askForReservation";
    }

    @PostMapping("/checkIfPossible")
    public String checkIfPossible(@Valid Reservation reservation, BindingResult result, Model model) {
        if (result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(err -> System.out.println(err.getDefaultMessage()));
            List<Restaurant> allRestaurants = restaurantService.getAll();
            model.addAttribute("allRestaurants", allRestaurants);
            model.addAttribute("reservation", reservation);
            return "askForReservation";
        }
        String isPossible = reservationService.isPossible(reservation);
        model.addAttribute("reservation", reservation);
        switch (isPossible) {
            case RESERVATION_AVAILABLE:
                return "success";
            case ALL_TABLES_ARE_OCCUPIED:
                return "findAnotherTable";
            case NO_SUCH_TABLE_AVAILABLE_IN_RESTAURANT:
                return "findAnotherRestaurant";
        }
        return "redirect:/";
    }

    @GetMapping("/showAllReservations")
    public String showAlReservations(Model model){
        List<Reservation> allReservations = reservationService.getAll();
        model.addAttribute("allReservations", allReservations);
        return "showAllReservations";
    }
}
