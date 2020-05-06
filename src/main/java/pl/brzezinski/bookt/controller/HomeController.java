package pl.brzezinski.bookt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @RequestMapping("/mainMenu")
    public String mainMenu(){
        return "mainMenu";
    }
}
