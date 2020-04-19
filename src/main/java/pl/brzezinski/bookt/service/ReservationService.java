package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.SingleTable;
import pl.brzezinski.bookt.repository.ReservationRepository;
import pl.brzezinski.bookt.repository.RestaurantRepository;
import pl.brzezinski.bookt.repository.TableRepository;

@Service
public class ReservationService {

    private RestaurantRepository restaurantRepository;
    private TableRepository tableRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RestaurantRepository restaurantRepository, TableRepository tableRepository, ReservationRepository reservationRepository) {
        this.restaurantRepository = restaurantRepository;
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    public void save(Reservation reservation){
        reservationRepository.save(reservation);
    }

    public Reservation assignTableToReservation(Reservation reservation, SingleTable singleTable) {
        reservation.setRestaurant(singleTable.getRestaurant());
        reservation.setSingleTable(singleTable);
        return reservation;
    }

}
