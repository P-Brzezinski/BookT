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
import java.util.Comparator;
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
        List<SchemaTable> possibleTables = findPossibleSchemaTables(reservation);
        if (possibleTables.isEmpty()) {
            return NO_SUCH_TABLE_AVAILABLE_IN_RESTAURANT;
        }
        //TODO check for reservations only on specifed table number
        List<ReservedTable> tablesReservedThisDay = reservedTableService.findAllByRestaurantAndDate(reservation.getRestaurant(), reservation.getDateTime().toLocalDate());
        if (tablesReservedThisDay.isEmpty()) {
            saveReservationOnTable(reservation, possibleTables.get(0));
            return RESERVATION_AVAILABLE;
        } else {
            checkTimeBetweenReservations(possibleTables, reservation);
            if (possibleTables.isEmpty()) {
                return ALL_TABLES_ARE_OCCUPIED_AT_THIS_TIME;
            } else {
                saveReservationOnTable(reservation, possibleTables.get(0));
                return RESERVATION_AVAILABLE;
            }
        }
    }

    private List<SchemaTable> findPossibleSchemaTables(Reservation reservation) {
        Restaurant restaurant = reservation.getRestaurant();
        int minPlacesAtTable = reservation.getNumberOfPersons() - restaurant.getMinPlaces();
        int maxTablesAtTable = reservation.getNumberOfPersons() + restaurant.getMaxPlaces();
        List<SchemaTable> possibleSchemaTables = schemaTableService.findAllByRestaurantsAndByPlacesBetween(restaurant, minPlacesAtTable, maxTablesAtTable);
        return possibleSchemaTables;
    }

    private List<SchemaTable> checkTimeBetweenReservations(List<SchemaTable> availableSchemaTables, Reservation reservation) {
        Restaurant restaurant = reservation.getRestaurant();
        List<ReservedTable> allReservations = reservedTableService.findAllByRestaurantAndDate(reservation.getRestaurant(), reservation.getDateTime().toLocalDate());
        List<SchemaTable> tablesNotFree = new ArrayList<>();

        for (SchemaTable availableTable : availableSchemaTables) {
            for (ReservedTable reservedTable : allReservations) {
                if ((availableTable.getTableNumber() == reservedTable.getTableNumber())
                        && (!reservedTable.getDateOfReservation().isAfter(reservation.getDateTime().plusMinutes(restaurant.getDefaultMinutesForReservation()).minusMinutes(1))
                        && (!reservedTable.getDateOfReservation().isBefore(reservation.getDateTime().minusMinutes(restaurant.getDefaultMinutesForReservation()).plusHours(1))))) {
                    tablesNotFree.add(availableTable);
                }
            }
        }
        availableSchemaTables.removeAll(tablesNotFree);
        return availableSchemaTables;
    }

    public ReservedTable findShortTermTable(Reservation reservation) {
        List<SchemaTable> schemaTables = findPossibleSchemaTables(reservation);
        Restaurant restaurant = reservation.getRestaurant();
        ReservedTable reservedTable = null;
        List<ReservedTable> findAllBefore = null;
        List<ReservedTable> findAllAfter = null;
        ReservedTable reservationOnSameTime = null;

        for (SchemaTable schemaTable : schemaTables) {
            findAllBefore = reservedTableService.findAllBefore(reservation, schemaTable.getTableNumber());
            findAllBefore.sort(Comparator.comparing(ReservedTable::getDateOfReservation).reversed());
            findAllAfter = reservedTableService.findAllAfter(reservation, schemaTable.getTableNumber());
            findAllAfter.sort(Comparator.comparing(ReservedTable::getDateOfReservation));
            reservationOnSameTime = reservedTableService.findIfAnyOnTheSameTime(reservation, schemaTable.getTableNumber());
        }

        if (reservationOnSameTime != null) {
            return reservedTable;
        } else if (findAllBefore.size() == 0 && findAllAfter.size() != 0) {
            ReservedTable tableAfter = findAllAfter.get(0);
            if (reservation.getDateTime().isBefore(tableAfter.getDateOfReservation().plusMinutes(restaurant.getMinimumMinutesForReservation()))) {
                reservedTable = tableAfter;
            }
        } else if (findAllBefore.size() != 0 && findAllAfter.size() == 0) {
            ReservedTable tableBefore = findAllBefore.get(0);
            if (reservation.getDateTime().isAfter(tableBefore.getDateOfReservation().plusMinutes(restaurant.getDefaultMinutesForReservation()))) {
                reservedTable = tableBefore;
            }
        } else if (findAllBefore.size() != 0 && findAllAfter.size() != 0) {
            ReservedTable tableAfter = findAllAfter.get(0);
            ReservedTable tableBefore = findAllBefore.get(0);
            if (reservation.getDateTime().isAfter(tableBefore.getDateOfReservation().plusMinutes(restaurant.getDefaultMinutesForReservation()))
                    && reservation.getDateTime().isBefore(tableAfter.getDateOfReservation().plusMinutes(restaurant.getMinimumMinutesForReservation()))) {
                reservedTable = tableAfter;
            }
        }
        return reservedTable;
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
