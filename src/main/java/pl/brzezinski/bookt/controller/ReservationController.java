package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.tables.ReservedTable;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.tables.SchemaTable;
import pl.brzezinski.bookt.service.ReservationService;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.SchemaTableService;
import pl.brzezinski.bookt.validation.constraint.groupSequences.FirstValidation;
import pl.brzezinski.bookt.validation.constraint.groupSequences.SecondValidation;

import java.time.format.DateTimeFormatter;
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
    public String askForReservation(@RequestParam(required = false) Long restaurantId, Model model) {
        if (restaurantId != null){
            Reservation reservation = new Reservation();
            Restaurant restaurant = restaurantService.get(restaurantId);
            reservation.setRestaurant(restaurant);
            model.addAttribute("reservation", reservation);
            model.addAttribute("restaurant", restaurant);
        }else {
            model.addAttribute("reservation", new Reservation());
        }
        List<Restaurant> allRestaurants = restaurantService.getAll();
        model.addAttribute("allRestaurants", allRestaurants);
        return "askForReservation";
    }

    @PostMapping("/firstValidation")
    public String firstValidation(@Validated(FirstValidation.class) Reservation reservation, BindingResult result, Model model) {
        model.addAttribute("reservation", reservation);
        if (result.hasErrors()) {
            List<Restaurant> allRestaurants = restaurantService.getAll();
            model.addAttribute("allRestaurants", allRestaurants);
            return "askForReservation";
        }
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
                return "reservationSuccess";
            case ALL_TABLES_ARE_OCCUPIED_AT_THIS_TIME:
                return "redirect:/findShortTermTable";
            default:
                return "failed";
        }
    }

    @GetMapping("/findShortTermTable")
    public String findShortTermTable(@ModelAttribute("reservation") Reservation reservation, Model model) {
        //check on which table there is a possibility for short term reservation
        ReservedTable reservedTable = reservationService.findShortTermTable(reservation);
        if (reservedTable == null) {
            model.addAttribute("reason", ALL_TABLES_ARE_OCCUPIED_AT_THIS_TIME);
            return "failed";
        } else {
            Long duration = reservationService.checkTimeBetween(reservation.getDateTime(), reservedTable.getDateOfReservation());
            if (duration < Restaurant.ESTIMATED_MINIMUM_TIME_FOR_ONE_RESERVATION_IN_MINUTES) {
                model.addAttribute("reason", ALL_TABLES_ARE_OCCUPIED_AT_THIS_TIME);
                return "failed";
            } else {
                //"convert" it to schema table
                SchemaTable schemaTable = schemaTableService.findByRestaurantAndTableNumber(reservation.getRestaurant(), reservedTable.getTableNumber());
                model.addAttribute("schemaTable", schemaTable);
                model.addAttribute("duration", duration);
                return "proposeShortTermTable";
            }
        }
    }

    @GetMapping("/saveReservation")
    public String saveReservation(@ModelAttribute("reservation") Reservation reservation, @RequestParam int schemaTableNumber, Model model, SessionStatus status) {
        SchemaTable schemaTable = schemaTableService.findByRestaurantAndTableNumber(reservation.getRestaurant(), schemaTableNumber);
        reservationService.saveReservationOnTable(reservation, schemaTable);
        status.setComplete();
        return "reservationSuccess";
    }

    @GetMapping("/admin/showAllReservations")
    public String showAlReservations(Model model) {
        List<Reservation> allReservations = reservationService.getAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd LLLL yyyy" );
        model.addAttribute("formatter", formatter);
        model.addAttribute("allReservations", allReservations);
        return "showAllReservations";
    }
}
