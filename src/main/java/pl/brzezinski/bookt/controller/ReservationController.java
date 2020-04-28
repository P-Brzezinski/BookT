package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.ReservedTable;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.service.ReservationService;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.SchemaTableService;
import pl.brzezinski.bookt.validation.constraint.groupSequences.CompleteValidation;
import pl.brzezinski.bookt.validation.constraint.groupSequences.FirstValidation;
import pl.brzezinski.bookt.validation.constraint.groupSequences.SecondValidation;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.List;

import static pl.brzezinski.bookt.service.ReservationService.*;

@Controller
@SessionAttributes("reservation")
public class ReservationController {

    private RestaurantService restaurantService;
    private ReservationService reservationService;
    private SchemaTableService schemaTableService;

    @Autowired
    public ReservationController(RestaurantService restaurantService, ReservationService reservationService, SchemaTableService schemaTableService) {
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
        this.schemaTableService = schemaTableService;
    }

    @GetMapping("/askForReservation")
    public String askForReservation(Model model) {
        List<Restaurant> allRestaurants = restaurantService.getAll();
        model.addAttribute("allRestaurants", allRestaurants);
        model.addAttribute("reservation", new Reservation());
        return "askForReservation";
    }

    @PostMapping("/firstValidation")
    public String firstValidation(@Validated(FirstValidation.class) Reservation reservation, BindingResult result, Model model){
        model.addAttribute("reservation", reservation);
        if (result.hasErrors()) {
            List<Restaurant> allRestaurants = restaurantService.getAll();
            model.addAttribute("allRestaurants", allRestaurants);
            return "askForReservation";
        }
        System.out.println(reservation.toString());
        return "redirect:checkIfPossible";
    }

    @GetMapping("/checkIfPossible")
    public String checkIfPossible(@Validated(SecondValidation.class) Reservation reservation, BindingResult result, Model model) {
        model.addAttribute("reservation", reservation);
        if (result.hasErrors()) {
            List<Restaurant> allRestaurants = restaurantService.getAll();
            model.addAttribute("allRestaurants", allRestaurants);
            return "askForReservation";
        }
        String isPossible = reservationService.checkIfPossible(reservation);
        model.addAttribute("reason", isPossible);
        switch (isPossible) {
            case RESERVATION_AVAILABLE:
                return "success";
            case ALL_TABLES_ARE_OCCUPIED_AT_THIS_TIME:
                return "redirect:/findShortTermTable";
            default:
                return "failed";
        }
    }

    @GetMapping("/findShortTermTable")
    public String findShortTermTable(@ModelAttribute("reservation") Reservation reservation, Model model) {
        //finds table which is reserved after your table with the longest duration between both
        ReservedTable reservedTable = reservationService.findShortTermTable(reservation);
        //"convert" it to schema table
        SchemaTable schemaTable = schemaTableService.findByRestaurantAndTableNumber(reservation.getRestaurant(), reservedTable.getTableNumber());
        Long duration = reservationService.checkTimeBetween(reservation.getDateTime(), reservedTable.getDateOfReservation());
        model.addAttribute("schemaTable", schemaTable);
        model.addAttribute("duration", duration);
        return "proposeShortTermTable";
    }

    @GetMapping("/saveReservation")
    public String saveReservation(@ModelAttribute("reservation") Reservation reservation, @RequestParam int schemaTableNumber, Model model, SessionStatus status) {
        SchemaTable schemaTable = schemaTableService.findByRestaurantAndTableNumber(reservation.getRestaurant(), schemaTableNumber);
        reservationService.addReservationOnTable(reservation, schemaTable);
//        model.addAttribute("reservation", reservation);
        status.setComplete();
        return "success";
    }

    @GetMapping("/showAllReservations")
    public String showAlReservations(Model model) {
        List<Reservation> allReservations = reservationService.getAll();
        model.addAttribute("allReservations", allReservations);
        return "showAllReservations";
    }
}
