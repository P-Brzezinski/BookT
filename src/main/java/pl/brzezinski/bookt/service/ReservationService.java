package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.ReservedTable;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.repository.ReservationRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static pl.brzezinski.bookt.model.Restaurant.ESTIMATED_TIME_BETWEEN_RESERVATIONS_IN_MINUTES;
import static pl.brzezinski.bookt.model.Restaurant.ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_HOURS;

@Service
public class ReservationService implements GenericRepository<Long, Reservation> {

    public static final String RESERVATION_AVAILABLE = "reservation available";
    public static final String NO_SUCH_TABLE_AVAILABLE_IN_RESTAURANT = "no table available for this number of persons";
    public static final String ALL_TABLES_ARE_OCCUPIED_AT_THIS_TIME = "all tables are occupied at this time";

    private ReservationRepository reservationRepository;
    private RestaurantService restaurantService;
    private ReservedTableService reservedTableService;
    private SchemaTableService schemaTableService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RestaurantService restaurantService, ReservedTableService reservedTableService, SchemaTableService schemaTableService) {
        this.reservationRepository = reservationRepository;
        this.restaurantService = restaurantService;
        this.reservedTableService = reservedTableService;
        this.schemaTableService = schemaTableService;
    }

    @Override
    public Reservation get(Long id) {
        return reservationRepository.getOne(id);
    }

    @Override
    public void add(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public void remove(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public String checkIfPossible(Reservation reservation) {
        String result;
        List<SchemaTable> availableSchemaTables = schemaTableService.findAllByRestaurantsAndByPlaces(reservation.getRestaurant(), reservation.getNumberOfPersons());
        List<ReservedTable> allReservations = reservedTableService.getAll();

        if (availableSchemaTables.isEmpty()) {
            result = NO_SUCH_TABLE_AVAILABLE_IN_RESTAURANT;
        } else {
            checkWhichTablesAreFreeInRestaurant(availableSchemaTables, allReservations, reservation);
            if (availableSchemaTables.isEmpty()) {
                result = ALL_TABLES_ARE_OCCUPIED_AT_THIS_TIME;
            } else {
                addReservationOnTable(reservation, availableSchemaTables.get(0));
                result = RESERVATION_AVAILABLE;
            }
        }
        return result;
    }

    private List<SchemaTable> checkWhichTablesAreFreeInRestaurant(List<SchemaTable> availableSchemaTables, List<ReservedTable> allReservationsInThisDay, Reservation reservation) {
        List<SchemaTable> tablesNotFree = new ArrayList<>();

        for (SchemaTable availableTable : availableSchemaTables) {
            for (ReservedTable reservedTable : allReservationsInThisDay) {
                if ((availableTable.getTableNumber() == reservedTable.getTableNumber())
                        && (!reservedTable.getDateOfReservation().isAfter(reservation.getDateTime().plusHours(ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_HOURS).minusMinutes(1))
                        && (!reservedTable.getDateOfReservation().isBefore(reservation.getDateTime().minusHours(ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_HOURS).plusHours(1))))) {
                    tablesNotFree.add(availableTable);
                }
            }
        }
        availableSchemaTables.removeAll(tablesNotFree);
        return availableSchemaTables;
    }

    public void addReservationOnTable(Reservation reservation, SchemaTable schemaTable) {
        ReservedTable reservedTable = new ReservedTable(
                schemaTable.getTableNumber(),
                schemaTable.getPlaces(),
                reservation.getDateTime());
        reservationRepository.save(reservation);
        reservedTable.setReservation(reservation);
        reservedTable.setRestaurant(reservation.getRestaurant());
        reservedTableService.add(reservedTable);
        reservation.setReservedTable(reservedTable);
        reservationRepository.save(reservation);
    }

    public ReservedTable findShortTermTable(Reservation reservation) {
        List<SchemaTable> schemaTables = schemaTableService.findAllByRestaurantsAndByPlaces(reservation.getRestaurant(), reservation.getNumberOfPersons());
        ReservedTable reservedTable = null;

        System.out.println("SCHEMA TABLES: " + schemaTables.toString());

        for (SchemaTable schemaTable : schemaTables) {

            List<ReservedTable> findAllBefore = reservedTableService.findAllBefore(reservation, schemaTable.getTableNumber());
            findAllBefore.sort(Comparator.comparing(ReservedTable::getDateOfReservation).reversed());
            System.out.println("-----------------------------------");
            System.out.println("TABLE NUMBER " + schemaTable.getTableNumber());
            System.out.println("BEFORE:");
            System.out.println(findAllBefore.toString());

            List<ReservedTable> findAllAfter = reservedTableService.findAllAfter(reservation, schemaTable.getTableNumber());
            findAllAfter.sort(Comparator.comparing(ReservedTable::getDateOfReservation));
            System.out.println("AFTER: ");
            System.out.println(findAllAfter.toString());

            ReservedTable checkIfInTheSameTime = reservedTableService.findIfAnyOnTheSameTime(reservation, schemaTable.getTableNumber());

            if (reservation.getDateTime().isAfter(findAllBefore.get(0).getDateOfReservation().plusHours(3))
            && reservation.getDateTime().isBefore(findAllAfter.get(0).getDateOfReservation().plusHours(1))) {
                if (checkIfInTheSameTime == null) {
                    System.out.println("MOŻLIWY STOLIK " + schemaTable.getTableNumber());
                    reservedTable = findAllAfter.get(0);
                } else {
                    System.out.println("TE SAME GODZINY");
                    System.out.println("STOLIK ZAREZERWOWANY O TEJ GODZINIE :" + checkIfInTheSameTime.toString());
                }
            }
        }
        return reservedTable;
    }

    public Long checkTimeBetween(LocalDateTime reservation, LocalDateTime nextReservation) {
        Duration duration = Duration.between(reservation, nextReservation);
        Long durationInMinutes = duration.toMinutes() - ESTIMATED_TIME_BETWEEN_RESERVATIONS_IN_MINUTES;
        return durationInMinutes;
    }
}
