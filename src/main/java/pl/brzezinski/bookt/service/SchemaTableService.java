package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.repository.SchemaTableRepository;

import java.util.List;

@Service
public class SchemaTableService {

    private SchemaTableRepository schemaTableRepository;

    @Autowired
    public SchemaTableService(SchemaTableRepository schemaTableRepository) {
        this.schemaTableRepository = schemaTableRepository;
    }

    public void saveSchemaTable(SchemaTable schemaTable){
        schemaTableRepository.save(schemaTable);
    }

    public List<SchemaTable> getAllSchemaTables(){
        return schemaTableRepository.findAll();
    }

    public List<SchemaTable> getAllSchemaTablesByRestaurant(Restaurant restaurant){
        return schemaTableRepository.findAllByRestaurant(restaurant);
    }
}

