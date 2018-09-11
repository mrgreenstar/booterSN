package com.mrgreenstar.sn;

import com.mrgreenstar.sn.Entity.Role;
import com.mrgreenstar.sn.Entity.User;
import com.mrgreenstar.sn.repositories.RoleRepository;
import com.mrgreenstar.sn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView regSuccess(@RequestParam String firstName, @RequestParam String lastName,
                                   @RequestParam String country, @RequestParam String email,
                                   @RequestParam String password, ModelAndView model) {
        // Если пользователь уже существует
        if (userRepository.findByEmail(email) != null) {
            model.addObject("error", "User already exists");
            model.setViewName("registration");
            return model;
        }
        User user = new User(firstName, lastName, country, email, password);
        Role usrRole = roleRepository.findRoleByRoleName("USER");
        if (usrRole == null) {
            usrRole = new Role("USER");
            roleRepository.save(usrRole);
        }
        user.setRole(usrRole);
        userRepository.save(user);
        model.setViewName("redirect:/login");
        return model;
    }
}
