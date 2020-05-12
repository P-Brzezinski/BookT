package pl.brzezinski.bookt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/mainMenu")
    public String mainMenu() {
        return "mainMenu";
    }

    @RequestMapping("/admin")
    public String admin(){return "admin";}

    @RequestMapping("/restaurateurPanel")
    public String restaurateur(Principal principal, Model model){
        model.addAttribute("principal", principal);
        return "restaurateurPanel";}

    @RequestMapping("/403")
    public String error403(){
        return "403";
    }
}

