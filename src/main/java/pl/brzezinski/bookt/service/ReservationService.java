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
        List<ReservedTable> shortTermTables = reservedTableService.findShortTermTables(reservation.getRestaurant(), reservation.getNumberOfPersons(), reservation.getDateTime(), reservation.getDateTime().plusHours(ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_HOURS));
        System.out.println("ALL TABLES " + shortTermTables.toString());
        shortTermTables.sort(Comparator.comparing(ReservedTable::getDateOfReservation));
        //take table with latest time before your reservation
        return shortTermTables.get(shortTermTables.size() - 1);
    }

    public Long checkTimeBetween(LocalDateTime reservation, LocalDateTime dateOfReservation) {
        Duration duration = Duration.between(reservation, dateOfReservation);
        Long durationInMinutes = duration.toMinutes();
        return durationInMinutes;
    }
}
