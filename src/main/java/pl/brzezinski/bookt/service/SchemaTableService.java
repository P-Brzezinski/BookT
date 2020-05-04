package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.repository.SchemaTableRepository;

import java.util.List;

@Service
public class SchemaTableService implements GenericRepository<Long, SchemaTable> {

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
    public List<SchemaTable> getAll() {
        return schemaTableRepository.findAll();
    }

    public List<SchemaTable> findAllByRestaurant(Restaurant restaurant){ return schemaTableRepository.findAllByRestaurant(restaurant);}

    public List<SchemaTable> findAllByPlaces(int places){ return schemaTableRepository.findAllByPlaces(places);}

    public List<SchemaTable> findAllByRestaurantsAndByPlaces(Restaurant restaurant, int places){
        return schemaTableRepository.findAllByRestaurantAndPlaces(restaurant, places);
    }

    public List<SchemaTable> findAllByRestaurantsAndByPlacesBetween(Restaurant restaurant, int min, int max){
        return schemaTableRepository.findAllByRestaurantAndPlacesBetween(restaurant, min, max);
    }

    public SchemaTable findByRestaurantAndTableNumber(Restaurant restaurant, int tableNumber){
        return schemaTableRepository.findByRestaurantAndAndTableNumber(restaurant, tableNumber);
    }
}

