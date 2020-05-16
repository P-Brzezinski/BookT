package pl.brzezinski.bookt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.brzezinski.bookt.service.ReservationService;
import pl.brzezinski.bookt.service.RestaurantService;

@Controller
public class AdminController {

    private ReservationService reservationService;
    private RestaurantService restaurantService;

    public AdminController(ReservationService reservationService, RestaurantService restaurantService) {
        this.reservationService = reservationService;
        this.restaurantService = restaurantService;
    }

    @RequestMapping("/admin")
    public String admin(){return "admin";}

}
