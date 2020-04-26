package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.ReservedTable;
import pl.brzezinski.bookt.service.RestaurantService;
import pl.brzezinski.bookt.service.SchemaTableService;
import pl.brzezinski.bookt.service.ReservedTableService;

import java.util.List;

@Controller
public class TableController {

    private ReservedTableService reservedTableService;

    @Autowired
    public TableController(ReservedTableService reservedTableService) {
        this.reservedTableService = reservedTableService;
    }

}
