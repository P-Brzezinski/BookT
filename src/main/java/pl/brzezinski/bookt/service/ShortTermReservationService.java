package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.tables.ReservedTable;
import pl.brzezinski.bookt.model.tables.SchemaTable;

import java.util.Comparator;
import java.util.List;

@Service
public class ShortTermReservationService {

    private ReservedTableService reservedTableService;
    private SchemaTableService schemaTableService;

    @Autowired
    public ShortTermReservationService(ReservedTableService reservedTableService, SchemaTableService schemaTableService) {
        this.reservedTableService = reservedTableService;
        this.schemaTableService = schemaTableService;
    }

    public ReservedTable findShortTermTable(Reservation reservation) {
        Restaurant restaurant = reservation.getRestaurant();
        ReservedTable reservedTable = null;
        List<SchemaTable> schemaTables = schemaTableService.findPossibleSchemaTablesForReservation(reservation);
        ReservedTable reservationOnSameTime = checkIfAnyReservationOnTheSameTime(schemaTables, reservation);
        List<ReservedTable> findAllBefore = findAllReservationBefore(schemaTables, reservation);
        List<ReservedTable> findAllAfter = findAllReservationAfter(schemaTables, reservation);

        if (reservationOnSameTime != null) {
            return null;
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

    private ReservedTable checkIfAnyReservationOnTheSameTime(List<SchemaTable> schemaTables, Reservation reservation) {
        ReservedTable reservationOnSameTime = null;
        for (SchemaTable schemaTable : schemaTables) {
            reservationOnSameTime = reservedTableService.findIfAnyOnTheSameTime(reservation, schemaTable.getTableNumber());
        }
        return reservationOnSameTime;
    }

    private List<ReservedTable> findAllReservationBefore(List<SchemaTable> schemaTables, Reservation reservation) {
        List<ReservedTable> findAllBefore = null;
        for (SchemaTable schemaTable : schemaTables) {
            findAllBefore = reservedTableService.findAllBefore(reservation, schemaTable.getTableNumber());
            findAllBefore.sort(Comparator.comparing(ReservedTable::getDateOfReservation).reversed());
        }
        return findAllBefore;
    }

    private List<ReservedTable> findAllReservationAfter(List<SchemaTable> schemaTables, Reservation reservation) {
        List<ReservedTable> findAllAfter = null;
        for (SchemaTable schemaTable : schemaTables) {
            findAllAfter = reservedTableService.findAllAfter(reservation, schemaTable.getTableNumber());
            findAllAfter.sort(Comparator.comparing(ReservedTable::getDateOfReservation));
        }
        return findAllAfter;
    }
}
