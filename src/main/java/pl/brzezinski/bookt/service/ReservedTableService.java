package pl.brzezinski.bookt.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.ReservedTable;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.repository.ReservationRepository;
import pl.brzezinski.bookt.repository.RestaurantRepository;
import pl.brzezinski.bookt.repository.ReservedTableRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservedTableService implements GenericRepository<Long, ReservedTable>{

    private ReservedTableRepository reservedTableRepository;

    @Autowired
    public ReservedTableService(ReservedTableRepository reservedTableRepository) {
        this.reservedTableRepository = reservedTableRepository;
    }

    @Override
    public ReservedTable get(Long id) {
        return reservedTableRepository.getOne(id);
    }

    @Override
    public void add(ReservedTable reservedTable) {
        reservedTableRepository.save(reservedTable);
    }

    @Override
    public void remove(ReservedTable reservedTable) {
        reservedTableRepository.delete(reservedTable);
    }

    @Override
    public List<ReservedTable> getAll() {
        return reservedTableRepository.findAll();
    }

    public List<ReservedTable> findAllBefore(Reservation reservation, int tableNumber){
        return reservedTableRepository.findAllByRestaurantAndTableNumberAndDateOfReservationIsBefore(reservation.getRestaurant(), tableNumber, reservation.getDateTime());
    }

    public List<ReservedTable> findAllAfter(Reservation reservation, int tableNumber){
        return reservedTableRepository.findAllByRestaurantAndTableNumberAndDateOfReservationIsAfter(reservation.getRestaurant(), tableNumber, reservation.getDateTime());
    }

    public ReservedTable findIfAnyOnTheSameTime(Reservation reservation, int tableNumber) {
        return reservedTableRepository.findByRestaurantAndTableNumberAndDateOfReservation(reservation.getRestaurant(), tableNumber, reservation.getDateTime());
    }
}
