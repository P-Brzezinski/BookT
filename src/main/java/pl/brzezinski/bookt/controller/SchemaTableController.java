package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/restaurateurPanel/addNewSchemaTable")
    public String addNewSchemaTable(@RequestParam Long restaurantId, Model model) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        SchemaTable schemaTable = new SchemaTable();
        schemaTable.setRestaurant(restaurant);
        model.addAttribute("schemaTable", schemaTable);
        return "addNewSchemaTable";
    }

    @PostMapping("/restaurateurPanel/saveSchemaTable")
    public String saveSchemaTable(@ModelAttribute SchemaTable schemaTable) {
        schemaTableService.add(schemaTable);
        return "redirect:/restaurateurPanel/showAllRestaurants";
    }

    @GetMapping("/restaurateurPanel/deleteSchemaTable")
    public String deleteSchemaTable(@RequestParam Long schemaTableId, Model model) {
        schemaTableService.deleteById(schemaTableId);
        return "redirect:/restaurateurPanel/showAllRestaurants";
    }

    @GetMapping("/restaurateurPanel/showSchemaTables")
    public String showSchemaTables(@RequestParam Long restaurantId, Model model) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        List<SchemaTable> schemaTables = schemaTableService.findAllByRestaurant(restaurant);
        model.addAttribute("schemaTables", schemaTables);
        model.addAttribute("restaurantId" ,restaurantId);
        return "showAllSchemaTablesInRestaurant";
    }
}
