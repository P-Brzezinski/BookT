package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SingleTable;
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

    @GetMapping("/showAllTablesInRestaurant")
    public String showAllTablesInRestaurant(@RequestParam Long restaurantId, Model model) {
        Restaurant findRestaurant = restaurantService.getRestaurant(restaurantId);
        List<SingleTable> allTablesInRestaurant = findRestaurant.getSingleTables();
        model.addAttribute("allTablesInRestaurant", allTablesInRestaurant);
        model.addAttribute("restaurantId", restaurantId);
        return "showAllTablesInRestaurant";
    }
}
