package pl.brzezinski.bookt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.brzezinski.bookt.model.users.User;
import pl.brzezinski.bookt.service.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute @Valid User user,
                          BindingResult bindingResult,
                          @RequestParam(required = false) String isRestaurateur,
                          Model model) {
        if (bindingResult.hasErrors())
            return "registerForm";
        if (isRestaurateur.equals("yes")) {
            userService.addWithRestaurateurRole(user);
        } else {
            userService.addWithUserRole(user);
        }
        model.addAttribute("user", user);
        return "registerSuccess";
    }
}
