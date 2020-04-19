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

    @GetMapping("/makeAReservation")
    public String makeAReservation(Model model) {
        List<Restaurant> allRestaurants = restaurantService.getAll();
        model.addAttribute("allRestaurants", allRestaurants);
        model.addAttribute("newReservation", new Reservation());
        return "makeAReservation";
    }

    @PostMapping("/checkIfPossible")
    public String checkIfPossible(@ModelAttribute Reservation newReservation) {
        List<SchemaTable> allavailableTables = schemaTableList(newReservation.getNumberOfPersons());
        System.out.println("DOSTEPNE STOLIKI " + allavailableTables.toString());
        List<SingleTable> tables = tableService.findAllByPlacesAndDateOfReservationAndRestaurant(
                newReservation.getNumberOfPersons(),
                newReservation.getDateTime(),
                newReservation.getRestaurant()
        );

        System.out.println("STOLIKI ZAREZERWOWANE TEGO DNIA, W DANEJ RESTAURACJI I NA DANA ILOSC OSOB " + tables.toString());

        if (allavailableTables.isEmpty()) { //STOLIK O PODANEJ LICZBIE LUDZI FIZYCZNIE NIE ISTNIEJE W RESTAURACJI
            System.out.println("Nie ma takiego stolika");
        } else {
            if (tables.isEmpty()) { // NIE MA ŻĄDNYCH ZAREZERWOWANYCH STOLIKÓW W RESTARACJI WIEC MOZNA OD RAZU ZAPISAC
                SingleTable table = new SingleTable(
                        newReservation.getNumberOfPersons(),
                        allavailableTables.get(0).getPlaces(),
                        newReservation.getDateTime());
                reservationService.save(newReservation);
                table.setReservation(newReservation);
                table.setRestaurant(newReservation.getRestaurant());
                tableService.saveTable(table);
                newReservation.setSingleTable(table);
                reservationService.save(newReservation);
                System.out.println("ZAPISANO STÓŁ W PUSTEJ BAZIE STOŁÓW " + table.toString());
            } else if (allavailableTables.size() == tables.size()) { //JEZELI LICZBA DOSTEPNYCH FIZYCZNIE STOLIKOW JEST TAKA SAMA JAK LISTA ZAREZERWOWANCH STOLIKOW TO ZNACZY ZE WSZYSTKIE SA ZAEJETE
                System.out.println("WSZYSTKIE STOLIKI SĄ ZAJĘTE");
            } else {
                for (SchemaTable schemaTable : allavailableTables) {
                    for (SingleTable reservedTable : tables) {
                        if (schemaTable.getTableNumber() == reservedTable.getTableNumber()) {
                            continue;
                        }else {
                            allavailableTables.remove(schemaTable);
                        }
                    }
                }
                SingleTable table = new SingleTable(
                        newReservation.getNumberOfPersons(),
                        allavailableTables.get(0).getPlaces(),
                        newReservation.getDateTime());
                reservationService.save(newReservation);
                table.setReservation(newReservation);
                table.setRestaurant(newReservation.getRestaurant());
                tableService.saveTable(table);
                newReservation.setSingleTable(table);
                reservationService.save(newReservation);
                System.out.println("ZAPISANO STÓŁ W PUSTEJ BAZIE STOŁÓW " + table.toString());

                System.out.println(allavailableTables.toString());
//                System.out.println("PRZED retainAll " + allavailableTables.toString());
//                allavailableTables.retainAll(tables);
//                System.out.println("PO retainALL " + allavailableTables.toString());
//                System.out.println("ZAPISANO STÓŁ Z PULI POZOSTALYCH PUSTYCH STOLOW " + table.toString());
            }
        }
        return "redirect:/";
    }

    private List<SchemaTable> schemaTableList(Long places) {
        return schemaTableService.findAllByPlaces(places);
    }
}
