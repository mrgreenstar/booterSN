package com.mrgreenstar.sn;

import com.mrgreenstar.sn.Entity.User;
import com.mrgreenstar.sn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class SettingsController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/settings")
    public ModelAndView settingsPage(Principal principal, ModelAndView model) {
        User user = userRepository.findUserByEmail(principal.getName());
        model.addObject("ownerId", user.getId());
        model.addObject("firstName", user.getFirstName());
        model.addObject("lastName", user.getLastName());
        model.addObject("country", user.getCountry());
        model.setViewName("settings");
        return model;
    }

    @PostMapping("/settings")
    public ModelAndView saveSettings(@RequestParam("firstName") String firstName,
                                     @RequestParam("lastName") String lastName,
                                     @RequestParam("country") String country,
                                     Principal principal, ModelAndView model) {
        User user = userRepository.findUserByEmail(principal.getName());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCountry(country);
        userRepository.save(user);
        model.addObject("info", "Changes have been saved");
        model.setViewName("settings");
        return model;
    }
}
