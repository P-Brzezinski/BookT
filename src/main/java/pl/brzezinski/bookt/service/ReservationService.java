package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.ReservedTable;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.repository.ReservationRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static pl.brzezinski.bookt.model.Restaurant.ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_HOURS;

@Service
public class ReservationService implements GenericRepository<Long, Reservation> {

    public static final String NO_SUCH_TABLE_AVAILABLE_IN_RESTAURANT = "Not available.";
    public static final String RESERVATION_AVAILABLE = "Reservation available.";
    public static final String ALL_TABLES_ARE_OCCUPIED = "All tables are occupied";
    public static final String OUTSIDE_OF_WORKING_HOURS = "You can not reserve table outside of restaurant working hours";

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

    public String isPossible(Reservation reservation) {
        String result;
        List<SchemaTable> availableSchemaTables = schemaTableService.findAllByRestaurantsAndByPlaces(reservation.getRestaurant(), reservation.getNumberOfPersons());
        List<ReservedTable> allReservations = reservedTableService.getAll();

        if (availableSchemaTables.isEmpty()) {
            result = NO_SUCH_TABLE_AVAILABLE_IN_RESTAURANT;
        } else {
            checkWhichTablesAreFree(availableSchemaTables, allReservations, reservation);
            if (availableSchemaTables.isEmpty()) {
                result = ALL_TABLES_ARE_OCCUPIED;
            } else {
                makeAReservationOnTable(reservation, availableSchemaTables.get(0));
                result = RESERVATION_AVAILABLE;
            }
        }
        return result;
    }

    private List<SchemaTable> checkWhichTablesAreFree(List<SchemaTable> availableSchemaTables, List<ReservedTable> allReservationsInThisDay, Reservation reservation) {
        List<SchemaTable> occupiedTablesFromAvailableTables = new ArrayList<>();

        for (SchemaTable availableTable : availableSchemaTables) {
            for (ReservedTable reservedTable : allReservationsInThisDay) {
                if ((availableTable.getTableNumber() == reservedTable.getTableNumber())
                        && (!reservedTable.getDateOfReservation().isAfter(reservation.getDateTime().plusHours(ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_HOURS).minusMinutes(1))
                        && (!reservedTable.getDateOfReservation().isBefore(reservation.getDateTime().minusHours(ESTIMATED_TIME_FOR_ONE_RESERVATION_IN_HOURS).plusHours(1))))) {
                    occupiedTablesFromAvailableTables.add(availableTable);
                }
            }
        }
        availableSchemaTables.removeAll(occupiedTablesFromAvailableTables);
        return availableSchemaTables;
    }

    private void makeAReservationOnTable(Reservation reservation, SchemaTable schemaTable) {
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
}
