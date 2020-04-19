package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.model.SingleTable;
import pl.brzezinski.bookt.repository.ReservationRepository;
import pl.brzezinski.bookt.repository.RestaurantRepository;
import pl.brzezinski.bookt.repository.SchemaTableRepository;
import pl.brzezinski.bookt.repository.TableRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    private TableService tableService;
    private SchemaTableService schemaTableService;
    private ReservationService reservationService;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, TableService tableService, SchemaTableService schemaTableService, ReservationService reservationService) {
        this.restaurantRepository = restaurantRepository;
        this.tableService = tableService;
        this.schemaTableService = schemaTableService;
        this.reservationService = reservationService;
    }

    public Restaurant getRestaurant(Long id) {
        return restaurantRepository.getOne(id);
    }

    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public boolean isPossible(Reservation newReservation) {
        boolean isPossible = true;

        List<SchemaTable> allavailableTables = schemaTableList(newReservation.getNumberOfPersons());
        List<SchemaTable> toRemove = new ArrayList<>();

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
                        allavailableTables.get(0).getTableNumber(),
                        allavailableTables.get(0).getPlaces(),
                        newReservation.getDateTime());
                reservationService.save(newReservation);
                table.setReservation(newReservation);
                table.setRestaurant(newReservation.getRestaurant());
                tableService.save(table);
                newReservation.setSingleTable(table);
                reservationService.save(newReservation);
                System.out.println("ZAPISANO STÓŁ W PUSTEJ BAZIE STOŁÓW " + table.toString());
            } else if (allavailableTables.size() == tables.size()) { //JEZELI LICZBA DOSTEPNYCH FIZYCZNIE STOLIKOW JEST TAKA SAMA JAK LISTA ZAREZERWOWANCH STOLIKOW TO ZNACZY ZE WSZYSTKIE SA ZAEJETE
                System.out.println("WSZYSTKIE STOLIKI SĄ ZAJĘTE");
            } else {
                for (SchemaTable schemaTable : allavailableTables) {
                    System.out.println("1. weż stolik z listy dostepnych " + schemaTable.toString());
                    for (SingleTable reservedTable : tables) {
                        System.out.println("2. sprawdz go z " + reservedTable.toString());
                        if (schemaTable.getTableNumber() == reservedTable.getTableNumber()) {
                            toRemove.add(schemaTable);
                        }
                    }
                }
                allavailableTables.removeAll(toRemove);
                System.out.println("3. stoly do wywalenia " + toRemove.toString());
                System.out.println("4. po wywaleniu zostalo zostalo ---> " + allavailableTables.toString());
                System.out.println(allavailableTables.get(0).toString());
                SingleTable table = new SingleTable(
                        allavailableTables.get(0).getTableNumber(),
                        allavailableTables.get(0).getPlaces(),
                        newReservation.getDateTime());
                reservationService.save(newReservation);
                table.setReservation(newReservation);
                table.setRestaurant(newReservation.getRestaurant());
                tableService.save(table);
                newReservation.setSingleTable(table);
                reservationService.save(newReservation);
                System.out.println("ZAPISANO STÓŁ W BAZIE DANYCH" + table.toString());

//                System.out.println(allavailableTables.toString());
//                System.out.println("PRZED retainAll " + allavailableTables.toString());
//                allavailableTables.retainAll(tables);
//                System.out.println("PO retainALL " + allavailableTables.toString());
//                System.out.println("ZAPISANO STÓŁ Z PULI POZOSTALYCH PUSTYCH STOLOW " + table.toString());
            }
        }
        return isPossible;
    }

    private List<SchemaTable> schemaTableList(Long places) {
        return schemaTableService.findAllByPlaces(places);
    }
}

