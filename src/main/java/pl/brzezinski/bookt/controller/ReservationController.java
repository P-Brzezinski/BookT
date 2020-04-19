package pl.brzezinski.bookt.controller;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.model.SingleTable;
import pl.brzezinski.bookt.service.ReservationService;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.SchemaTableService;
import pl.brzezinski.bookt.service.TableService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private TableService tableService;
    private ReservationService reservationService;
    private RestaurantService restaurantService;
    private SchemaTableService schemaTableService;

    @Autowired
    public ReservationController(TableService tableService, ReservationService reservationService, RestaurantService restaurantService, SchemaTableService schemaTableService) {
        this.tableService = tableService;
        this.reservationService = reservationService;
        this.restaurantService = restaurantService;
        this.schemaTableService = schemaTableService;
    }

    @GetMapping("/askForReservation")
    public String askForReservation(Model model) {
        List<Restaurant> allRestaurants = restaurantService.getAll();
        model.addAttribute("allRestaurants", allRestaurants);
        model.addAttribute("newReservation", new Reservation());
        return "askForReservation";
    }

    @PostMapping("/checkIfPossible")
    public String checkIfPossible(@ModelAttribute Reservation newReservation) {
        boolean isPossible = restaurantService.isPossible(newReservation);
//        if (isPossible) {
//            return "summaryOfPossibleReservation";
//        } else {
//            return "summaryOfNotPossibleReservation";
//        }
        return "redirect:/";
    }
}
