package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.tables.ReservedTable;
import pl.brzezinski.bookt.repository.ReservedTableRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservedTableService implements GenericService<Long, ReservedTable> {

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
    public void deleteById(Long id) {
        reservedTableRepository.deleteById(id);
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

    public List<ReservedTable> findAllByRestaurantAndDate(Restaurant restaurant, LocalDate date){
        LocalDateTime timeStart = date.atTime(restaurant.getOpenTime().getHour(), restaurant.getOpenTime().getMinute());
        System.out.println("TIME START: " + timeStart);
        LocalDateTime timeEnds = date.atTime(restaurant.getCloseTime().getHour(), restaurant.getCloseTime().getMinute());
        System.out.println("TIME ENDS: " + timeEnds);
        return reservedTableRepository.findAllByRestaurantAndDateOfReservationBetween(restaurant, timeStart, timeEnds);
    }
}
