package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.Table;
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

    public Table getTable(Long id) {
        return tableRepository.getOne(id);
    }

    public List<Table> findAvailableTablesInRestaurantByDateTime(Long restaurantId, LocalDateTime localDateTime) {
        Restaurant findRestaurant = restaurantRepository.getOne(restaurantId);
        List<Table> findTables = findRestaurant.getTables();
        checkIfOccupied(findTables, localDateTime);
        return findTables;
    }

    private List<Table> checkIfOccupied(List<Table> tables, LocalDateTime localDateTime) {
        for (Table table : tables) {
            if (table.getDateOfReservation().isEqual(localDateTime)) {
                table.setIsOccupied(isTableOccupied.TRUE);
            } else {
                table.setIsOccupied(isTableOccupied.FALSE);
            }
        }
        return tables;
    }

    public void changeOccupyOfTable(Long id) {
        Table table = tableRepository.getOne(id);
        table.setIsOccupied(isTableOccupied.TRUE);
        tableRepository.save(table);
    }
}
