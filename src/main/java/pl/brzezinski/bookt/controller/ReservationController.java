package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.Table;
import pl.brzezinski.bookt.service.ReservationService;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.TableService;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ReservationController {

    private TableService tableService;
    private ReservationService reservationService;
    private RestaurantService restaurantService;

    @Autowired
    public ReservationController(TableService tableService, ReservationService reservationService, RestaurantService restaurantService) {
        this.tableService = tableService;
        this.reservationService = reservationService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/makeAReservation")
    public String makeAReservation(Model model){
        List<Restaurant> allRestaurants = restaurantService.getAll();
        model.addAttribute("allRestaurants", allRestaurants);
        model.addAttribute("newReservation", new Reservation());
        return "makeAReservation";
    }

    @PostMapping("/checkIfPossible")
    public String checkIfPossible(@ModelAttribute Reservation newReservation){
        System.out.println(newReservation.toString());
        return "redirect:/";
    }
}
