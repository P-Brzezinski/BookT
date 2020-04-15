package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.brzezinski.bookt.model.Reservation;
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

    @GetMapping("/chooseDate")
    public String chooseDate(@RequestParam Long restaurantId, Model model){
        model.addAttribute("restaurantId", restaurantId);
        return "chooseDate";
    }

    @PostMapping("/check")
    public String checkAvailableTables(@RequestParam Long restaurantId, @RequestParam String reservationDate, Model model) throws ParseException {
        LocalDateTime dateTime = LocalDateTime.parse(reservationDate, DateTimeFormatter.ISO_DATE_TIME);
        List<Table> listOfTables = tableService.findAvailableTablesInRestaurantByDateTime(restaurantId, dateTime);
        model.addAttribute("allTablesInRestaurant", listOfTables);
        return "showAllTablesInRestaurant";
    }

    @GetMapping("/makeAReservation")
    public String makeAReservation(@RequestParam Long tableId, Model model){
        Table table = tableService.getTable(tableId);
        Reservation newReservation = new Reservation();
        reservationService.assignTableToReservation(newReservation, table);
        model.addAttribute("newReservation", newReservation);
        return "addReservationForm";
    }

    @PostMapping("/saveReservation")
    public String saveReservation(@ModelAttribute Reservation newReservation){
        tableService.changeOccupyOfTable(newReservation.getTable().getId());
        reservationService.save(newReservation);
        return "redirect:/";
    }
}
