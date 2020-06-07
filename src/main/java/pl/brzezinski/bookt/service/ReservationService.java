package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.tables.ReservedTable;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.tables.SchemaTable;
import pl.brzezinski.bookt.repository.ReservationRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService implements GenericService<Long, Reservation> {

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
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public String checkIfPossible(Reservation reservation) {
        List<SchemaTable> possibleTables = schemaTableService.findPossibleSchemaTablesForReservation(reservation);
        List<ReservedTable> tablesReservedThisDay = reservedTableService.findAllByRestaurantAndDate(reservation.getRestaurant(), reservation.getDateTime().toLocalDate());

        if (possibleTables.isEmpty()) {
            return NO_SUCH_TABLE_AVAILABLE_IN_RESTAURANT;
        } else if (tablesReservedThisDay.isEmpty()) {
            saveReservationOnTable(reservation, possibleTables.get(0));
            return RESERVATION_AVAILABLE;
        } else {
            possibleTables = checkTimeBetweenReservations(possibleTables, reservation);
            if (possibleTables.isEmpty()) {
                return ALL_TABLES_ARE_OCCUPIED_AT_THIS_TIME;
            } else {
                saveReservationOnTable(reservation, possibleTables.get(0));
                return RESERVATION_AVAILABLE;
            }
        }
    }

    private List<SchemaTable> checkTimeBetweenReservations(List<SchemaTable> availableSchemaTables, Reservation reservation) {
        List<ReservedTable> allReservations = reservedTableService.findAllByRestaurantAndDate(reservation.getRestaurant(), reservation.getDateTime().toLocalDate());
        List<SchemaTable> tablesNotFree = new ArrayList<>();

        //check every possible table if not reserved on the same time or right after or right before reservation
        for (SchemaTable possibleTable : availableSchemaTables) {
            for (ReservedTable reservedTable : allReservations) {
                if (isSameTable(possibleTable, reservedTable)
                        // check if right after
                        && isReservedAfterReservation(reservedTable, reservation)
                        // or check if right before
                        && isReservedBeforeReservation(reservedTable, reservation))
                        // if table not suitable, remove from possible list
                    tablesNotFree.add(possibleTable);
            }
        }
        availableSchemaTables.removeAll(tablesNotFree);
        return availableSchemaTables;

    }
//   old loop, delete after tests
//   if ((possibleTable.getTableNumber() == reservedTable.getTableNumber())
//   && (!reservedTable.getDateOfReservation().isAfter(reservation.getDateTime().plusMinutes(restaurant.getDefaultMinutesForReservation()).minusMinutes(1))
//   && (!reservedTable.getDateOfReservation().isBefore(reservation.getDateTime().minusMinutes(restaurant.getDefaultMinutesForReservation()).plusMinutes(1))))) {


    private boolean isSameTable(SchemaTable possibleTable, ReservedTable reservedTable) {
        return possibleTable.getTableNumber() == reservedTable.getTableNumber();
    }

    private boolean isReservedAfterReservation(ReservedTable reservedTable, Reservation reservation) {
        return !reservedTable.getDateOfReservation().isAfter(reservation.getDateTime().plusMinutes(reservation.getRestaurant().getDefaultMinutesForReservation()).minusMinutes(1));
    }

    private boolean isReservedBeforeReservation(ReservedTable reservedTable, Reservation reservation) {
        return !reservedTable.getDateOfReservation().isBefore(reservation.getDateTime().minusMinutes(reservation.getRestaurant().getDefaultMinutesForReservation()).plusMinutes(1));
    }

    public void saveReservationOnTable(Reservation reservation, SchemaTable schemaTable) {
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

    public Long checkTimeBetween(Reservation reservation, LocalDateTime nextReservation) {
        Duration duration = Duration.between(reservation.getDateTime(), nextReservation);
        Long durationInMinutes = duration.toMinutes() - reservation.getRestaurant().getMinutesBetweenReservations();
        return durationInMinutes;
    }
}
