package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.model.SingleTable;
import pl.brzezinski.bookt.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {


    public static final String NO_SUCH_TABLE_AVAILABLE_IN_RESTAURANT = "Not available.";
    public static final String RESERVATION_AVAILABLE = "Reservation available.";
    public static final String ALL_TABLES_ARE_OCCUPIED = "All tables are occupied";
    public static final int ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_HOURS = 3;

    private ReservationRepository reservationRepository;
    private RestaurantService restaurantService;
    private TableService tableService;
    private SchemaTableService schemaTableService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RestaurantService restaurantService, TableService tableService, SchemaTableService schemaTableService) {
        this.reservationRepository = reservationRepository;
        this.restaurantService = restaurantService;
        this.tableService = tableService;
        this.schemaTableService = schemaTableService;
    }

    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public String isPossible(Reservation newReservation) {
        String result;
        SchemaTable schemaTable;

        List<SchemaTable> availableSchemaTables = schemaTableService.findAllByRestaurantsAndByPlaces(newReservation.getRestaurant(), newReservation.getNumberOfPersons());
        List<SingleTable> allReservations = tableService.getAllTables();

        System.out.println("-----------------------------------------------------");
        System.out.println("SCHEMA TABLES " + availableSchemaTables.toString());
        System.out.println("ALL RESERVATIONS: " + allReservations.toString());

        if (availableSchemaTables.isEmpty()) {
            System.out.println("----> 1");
            result = NO_SUCH_TABLE_AVAILABLE_IN_RESTAURANT;
        } else {
            availableSchemaTables = checkWhichTablesAreFree(availableSchemaTables, allReservations, newReservation);
            System.out.println("SCHEMA TABLES AFTER FILTERING " + availableSchemaTables.toString());
            if (availableSchemaTables.isEmpty()) {
                result = ALL_TABLES_ARE_OCCUPIED;
            } else {
                schemaTable = availableSchemaTables.get(0);
                makeAReservationOnTable(newReservation, schemaTable);
                result = RESERVATION_AVAILABLE;
            }
        }
        return result;
    }

    private List<SchemaTable> checkWhichTablesAreFree(List<SchemaTable> availableSchemaTables, List<SingleTable> allReservationsInThisDay, Reservation reservation) {
        List<SchemaTable> occupiedTablesFromAvailableTables = new ArrayList<>();

        for (SchemaTable availableTable : availableSchemaTables) {
            for (SingleTable reservedTable : allReservationsInThisDay) {
                System.out.println("Zarezerwowany stolik numer " + reservedTable.getTableNumber());
                System.out.println("Sprawdz numery stolikow: " + (availableTable.getTableNumber() == reservedTable.getTableNumber()));
                System.out.println("Sprawdz godzine przed " + (!reservedTable.getDateOfReservation().isAfter(reservation.getDateTime().plusHours(3))));
                System.out.println("Sprawdz godzine po " + (!reservedTable.getDateOfReservation().isBefore(reservation.getDateTime().minusHours(3))));
                System.out.println("Sprawdz razem " + (!reservedTable.getDateOfReservation().isAfter(reservation.getDateTime().plusHours(3))
                        && (!reservedTable.getDateOfReservation().isBefore(reservation.getDateTime().minusHours(3)))));
                if ((availableTable.getTableNumber() == reservedTable.getTableNumber())
                && (!reservedTable.getDateOfReservation().isAfter(reservation.getDateTime().plusHours(3))
                && (!reservedTable.getDateOfReservation().isBefore(reservation.getDateTime().minusHours(3))))){
                    System.out.println("WYRZUC STOL " + reservedTable.toString());
                    occupiedTablesFromAvailableTables.add(availableTable);
                }
            }
        }
        availableSchemaTables.removeAll(occupiedTablesFromAvailableTables);
        return availableSchemaTables;
    }

    private void makeAReservationOnTable(Reservation reservation, SchemaTable schemaTable) {
        SingleTable reservedTable = new SingleTable(
                schemaTable.getTableNumber(),
                schemaTable.getPlaces(),
                reservation.getDateTime());
        reservationRepository.save(reservation);
        reservedTable.setReservation(reservation);
        reservedTable.setRestaurant(reservation.getRestaurant());
        tableService.save(reservedTable);
        reservation.setSingleTable(reservedTable);
        reservationRepository.save(reservation);
    }

}
