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

    public void save(SingleTable table){
        tableRepository.save(table);
    }

    public SingleTable getTable(Long id) {
        return tableRepository.getOne(id);
    }

    public List<SingleTable> getAllTables(){
        return tableRepository.findAll();
    }

    public List<SingleTable> findALlByRestaurant(Restaurant restaurant){
        return tableRepository.findAllByRestaurant(restaurant);
    }

    public List<SingleTable> findAllByPlacesAndDateOfReservationAndRestaurant(Long places, LocalDateTime dateOfReservation, Restaurant restaurant){
        return tableRepository.findAllByPlacesAndDateOfReservationAndRestaurant(places, dateOfReservation, restaurant);
    }


}
