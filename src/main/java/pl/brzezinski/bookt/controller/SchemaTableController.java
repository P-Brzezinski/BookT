package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.tables.SchemaTable;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.SchemaTableService;

import java.util.List;

@Controller
public class SchemaTableController {

    private RestaurantService restaurantService;
    private SchemaTableService schemaTableService;

    @Autowired
    public SchemaTableController(RestaurantService restaurantService, SchemaTableService schemaTableService) {
        this.restaurantService = restaurantService;
        this.schemaTableService = schemaTableService;
    }

    @GetMapping("/addNewTableSchema")
    public String addNewSchemaTable(@RequestParam Long restaurantId, Model model){
        Restaurant restaurant = restaurantService.get(restaurantId);
        SchemaTable schemaTable = new SchemaTable();
        schemaTable.setRestaurant(restaurant);
        model.addAttribute("schemaTable", schemaTable);
        return "addNewSchemaTable";
    }

    @PostMapping("/saveSchemaTable")
    public String saveSchemaTable(@ModelAttribute SchemaTable schemaTable){
        schemaTableService.add(schemaTable);
        return "redirect:/restaurateurPanel";
    }


    @GetMapping("/showSchemaTables")
    public String showSchemaTables(@RequestParam Long restaurantId, Model model){
        Restaurant restaurant = restaurantService.get(restaurantId);
        List<SchemaTable> schemaTables = schemaTableService.findAllByRestaurant(restaurant);
        model.addAttribute("schemaTables", schemaTables);
        return "showAllSchemaTablesInRestaurant";
    }
}
