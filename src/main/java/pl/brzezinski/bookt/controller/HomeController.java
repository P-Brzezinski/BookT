package pl.brzezinski.bookt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("admin", "admin");
        model.addAttribute("user", "user");
        return "index";
    }
}
