package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.tables.SchemaTable;
import pl.brzezinski.bookt.repository.SchemaTableRepository;

import java.util.List;

@Service
public class SchemaTableService implements GenericService<Long, SchemaTable> {

    private SchemaTableRepository schemaTableRepository;

    @Autowired
    public SchemaTableService(SchemaTableRepository schemaTableRepository) {
        this.schemaTableRepository = schemaTableRepository;
    }

    @Override
    public SchemaTable get(Long id) {
        return schemaTableRepository.getOne(id);
    }

    @Override
    public void add(SchemaTable schemaTable) {
        schemaTableRepository.save(schemaTable);
    }

    @Override
    public void remove(SchemaTable schemaTable) {
        schemaTableRepository.delete(schemaTable);
    }

    @Override
    public void deleteById(Long id) {
        schemaTableRepository.deleteById(id);
    }

    @Override
    public List<SchemaTable> getAll() {
        return schemaTableRepository.findAll();
    }

    public List<SchemaTable> findAllByRestaurant(Restaurant restaurant){ return schemaTableRepository.findAllByRestaurant(restaurant);}

    public List<SchemaTable> findAllByRestaurantsAndByPlacesBetween(Restaurant restaurant, int min, int max){
        return schemaTableRepository.findAllByRestaurantAndPlacesBetween(restaurant, min, max);
    }

    public SchemaTable findByRestaurantAndTableNumber(Restaurant restaurant, int tableNumber){
        return schemaTableRepository.findByRestaurantAndAndTableNumber(restaurant, tableNumber);
    }

    public List<SchemaTable> findPossibleSchemaTablesForReservation(Reservation reservation) {
        Restaurant restaurant = reservation.getRestaurant();
        int minPlacesAtTable = reservation.getNumberOfPersons() - restaurant.getMinPlaces();
        int maxTablesAtTable = reservation.getNumberOfPersons() + restaurant.getMaxPlaces();
        List<SchemaTable> possibleSchemaTables = findAllByRestaurantsAndByPlacesBetween(restaurant, minPlacesAtTable, maxTablesAtTable);
        return possibleSchemaTables;
    }
}

