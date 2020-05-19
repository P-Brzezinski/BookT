package pl.brzezinski.bookt.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/mainMenu")
    public String mainMenu(Principal principal, Model model) {
        model.addAttribute("principal", principal);
        return "mainMenu";
    }

    @RequestMapping("/restaurateurPanel")
    public String restaurateur(Principal principal, Model model) {
        model.addAttribute("principal", principal);
        return "restaurateurPanel";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/403")
    public String error403() {
        return "403";
    }
}

