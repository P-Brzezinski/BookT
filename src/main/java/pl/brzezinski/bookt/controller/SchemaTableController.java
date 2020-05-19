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
@SessionAttributes("restaurant")
@RequestMapping("/restaurateurPanel")
public class SchemaTableController {

    private RestaurantService restaurantService;
    private SchemaTableService schemaTableService;

    @Autowired
    public SchemaTableController(RestaurantService restaurantService, SchemaTableService schemaTableService) {
        this.restaurantService = restaurantService;
        this.schemaTableService = schemaTableService;
    }

    @ModelAttribute
    public Restaurant restaurant(){
        return new Restaurant();
    }

    @GetMapping("/showSchemaTables")
    public String showSchemaTables(@ModelAttribute("restaurant") Restaurant restaurant, @RequestParam(required = false) Long restaurantId, Model model) {
        if (restaurant.getId() == null){
            restaurant = restaurantService.get(restaurantId);
            model.addAttribute("restaurant", restaurant);
        }
        List<SchemaTable> schemaTables = schemaTableService.findAllByRestaurant(restaurant);
        model.addAttribute("schemaTables", schemaTables);
        return "showSchemaTables";
    }

    @GetMapping("/schemaTableForm")
    public String schemaTableForm(@ModelAttribute("restaurant") Restaurant restaurant, @RequestParam(required = false) Long schemaTableId, Model model) {
        SchemaTable schemaTable;
        if (schemaTableId == null){
            schemaTable = new SchemaTable();
            schemaTable.setRestaurant(restaurant);
        }else {
            schemaTable = schemaTableService.get(schemaTableId);
        }
        model.addAttribute("schemaTable", schemaTable);
        return "schemaTableForm";
    }

    @PostMapping("/saveSchemaTable")
    public String saveSchemaTable(@ModelAttribute SchemaTable schemaTable) {
        schemaTableService.add(schemaTable);
        return "redirect:/restaurateurPanel/showSchemaTables";
    }

    @GetMapping("/deleteSchemaTable")
    public String deleteSchemaTable(@RequestParam Long schemaTableId) {
        schemaTableService.deleteById(schemaTableId);
        return "redirect:/restaurateurPanel/showSchemaTables";
    }
}
