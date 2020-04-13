package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SingleTable;
import pl.brzezinski.bookt.model.enums.isTableOccupied;
import pl.brzezinski.bookt.repository.ReservationRepository;
import pl.brzezinski.bookt.repository.RestaurantRepository;
import pl.brzezinski.bookt.repository.TableRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TableService {

    private RestaurantRepository restaurantRepository;
    private TableRepository tableRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public TableService(RestaurantRepository restaurantRepository, TableRepository tableRepository, ReservationRepository reservationRepository) {
        this.restaurantRepository = restaurantRepository;
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    public SingleTable getTable(Long id) {
        return tableRepository.getOne(id);
    }

    public List<SingleTable> findAvailableTablesInRestaurantByDateTime(Long restaurantId, LocalDateTime localDateTime) {
        Restaurant findRestaurant = restaurantRepository.getOne(restaurantId);
        List<SingleTable> findTables = findRestaurant.getTables();
        checkIfOccupied(findTables, localDateTime);
        return findTables;
    }

    private List<SingleTable> checkIfOccupied(List<SingleTable> tables, LocalDateTime localDateTime) {
        for (SingleTable table : tables) {
            if (table.getDateOfReservation().isEqual(localDateTime)) {
                table.setIsOccupied(isTableOccupied.TRUE);
            } else {
                table.setIsOccupied(isTableOccupied.FALSE);
            }
        }
        return tables;
    }

    public void changeOccupyOfTable(Long id) {
        SingleTable table = tableRepository.getOne(id);
        table.setIsOccupied(isTableOccupied.TRUE);
        tableRepository.save(table);
    }
}
