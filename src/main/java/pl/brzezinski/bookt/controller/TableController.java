package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.model.Table;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.SchemaTableService;
import pl.brzezinski.bookt.service.TableService;

import java.util.List;

@Controller
public class TableController {

    private TableService tableService;
    private RestaurantService restaurantService;
    private SchemaTableService schemaTableService;

    @Autowired
    public TableController(TableService tableService, RestaurantService restaurantService, SchemaTableService schemaTableService) {
        this.tableService = tableService;
        this.restaurantService = restaurantService;
        this.schemaTableService = schemaTableService;
    }

    @GetMapping("/addTableSchema")
    public String addNewSchemaTable(@RequestParam Long restaurantId, Model model){
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        SchemaTable schemaTable = new SchemaTable();
        schemaTable.setRestaurant(restaurant);
        model.addAttribute("schemaTable", schemaTable);
        return "addSchemaTable";
    }

    @PostMapping("/saveSchemaTable")
    public String saveSchemaTable(@ModelAttribute SchemaTable schemaTable){
        schemaTableService.saveSchemaTable(schemaTable);
        return "redirect:/";
    }

    @GetMapping("/showSchemaTables")
    public String showSchemaTables(@RequestParam Long restaurantId, Model model){
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        List<SchemaTable> schemaTables = schemaTableService.getAllSchemaTablesByRestaurant(restaurant);
        model.addAttribute("schemaTables", schemaTables);
        return "showAllSchemaTablesInRestaurant";
    }

    @GetMapping("/showAllTablesInRestaurant")
    public String showAllTablesInRestaurant(@RequestParam Long restaurantId, Model model) {
        Restaurant findRestaurant = restaurantService.getRestaurant(restaurantId);
        List<Table> allTablesInRestaurant = findRestaurant.getTables();
        model.addAttribute("allTablesInRestaurant", allTablesInRestaurant);
        model.addAttribute("restaurantId", restaurantId);
        return "showAllTablesInRestaurant";
    }
}
