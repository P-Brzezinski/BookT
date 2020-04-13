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
import pl.brzezinski.bookt.model.SingleTable;
import pl.brzezinski.bookt.model.enums.isTableOccupied;
import pl.brzezinski.bookt.repository.ReservationRepository;
import pl.brzezinski.bookt.repository.RestaurantRepository;
import pl.brzezinski.bookt.repository.TableRepository;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ReservationController {

    private RestaurantRepository restaurantDAO;
    private TableRepository tableDAO;
    private ReservationRepository reservationDAO;

    @Autowired
    public ReservationController(RestaurantRepository restaurantDAO, TableRepository tableDAO, ReservationRepository reservationDAO) {
        this.restaurantDAO = restaurantDAO;
        this.tableDAO = tableDAO;
        this.reservationDAO = reservationDAO;
    }

    @GetMapping("/chooseDate")
    public String chooseDate(@RequestParam Long restaurantId, Model model){
        model.addAttribute("restaurantId", restaurantId);
        return "chooseDate";
    }

    private List<SingleTable> returnListOfTables(Long restaurantId){
        List<SingleTable> listOfTables = restaurantDAO.getOne(restaurantId).getTables();
        return listOfTables;
    }

    @PostMapping("/check")
    public String checkAvailableTables(@RequestParam Long restaurantId, @RequestParam String reservationDate, Model model) throws ParseException {
        LocalDateTime dateTime = LocalDateTime.parse(reservationDate, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println(reservationDate);
        System.out.println(restaurantId);
        List<SingleTable> listOfTables = findTablesInRestaurant(restaurantId, dateTime);
        model.addAttribute("allTablesInRestaurant", listOfTables);
        return "showAllTablesInRestaurant";
    }

    private List<SingleTable> findTablesInRestaurant(Long restaurantId, LocalDateTime localDateTime){
        Restaurant findRestaurant = restaurantDAO.getOne(restaurantId);
        List<SingleTable> findTables = findRestaurant.getTables();
        checkIfOccupied(findTables, localDateTime);
        return findTables;
    }

    private List<SingleTable> checkIfOccupied(List<SingleTable> tables, LocalDateTime localDateTime){
        for (SingleTable table : tables){
            if (table.getDateOfReservation().isEqual(localDateTime)){
                table.setIsOccupied(isTableOccupied.TRUE);
            }else {
                table.setIsOccupied(isTableOccupied.FALSE);
            }
        }
        return tables;
    }

    @GetMapping("/makeAReservation")
    public String makeAReservation(@RequestParam Long tableId, Model model){
        SingleTable table = tableDAO.getOne(tableId);
        Reservation newReservation = new Reservation();
        newReservation.setRestaurant(table.getRestaurant());
        newReservation.setTable(table);

        model.addAttribute("newReservation", newReservation);
        return "addReservationForm";
    }

    @PostMapping("/saveReservation")
    public String saveReservation(@ModelAttribute Reservation newReservation){
        changeOccupyOfTable(newReservation.getTable().getId());
        reservationDAO.save(newReservation);
        System.out.println(newReservation.toString());
        System.out.println(newReservation.getRestaurant().toString());
        System.out.println(newReservation.getTable().toString());
        return "redirect:/";
    }

    private void changeOccupyOfTable(Long id) {
        SingleTable table = tableDAO.getOne(id);
        table.setIsOccupied(isTableOccupied.TRUE);
        tableDAO.save(table);
    }
}
