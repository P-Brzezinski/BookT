package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.brzezinski.bookt.service.ReservedTableService;

@Controller
public class ReservedTableController {

    private ReservedTableService reservedTableService;

    @Autowired
    public ReservedTableController(ReservedTableService reservedTableService) {
        this.reservedTableService = reservedTableService;
    }

}
