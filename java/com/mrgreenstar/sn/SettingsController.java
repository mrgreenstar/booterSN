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
        model = addObjectsInModel(model, user);
        model.setViewName("settings");
        return model;
    }

    @PostMapping("/settings")
    public ModelAndView saveSettings(@RequestParam("firstName") String firstName,
                                     @RequestParam("lastName") String lastName,
                                     @RequestParam("country") String country,
                                     @RequestParam("email") String email,
                                     Principal principal, ModelAndView model) {
        User user = userRepository.findUserByEmail(principal.getName());
        model = addObjectsInModel(model, user);
        if (!user.getFirstName().equals(firstName)) {
            user.setFirstName(firstName);
        }
        if (!user.getLastName().equals(lastName)) {
            user.setLastName(lastName);
        }
        if (!user.getCountry().equals(country)) {
            user.setCountry(country);
        }
        // We need to be sure that this email wasn't already taken by somebody else
        if (!user.getEmail().equals(email) && userRepository.findUserByEmail(email) == null) {
            user.setEmail(email);
            userRepository.save(user);
            model.addObject("info", "Changes have been saved");
        }
        else model.addObject("emailError", "Error was occurred. This email is already taken");
        model.setViewName("settings");
        return model;
    }

    private ModelAndView addObjectsInModel(ModelAndView model, User user) {
        model.addObject("ownerId", user.getId());
        model.addObject("firstName", user.getFirstName());
        model.addObject("lastName", user.getLastName());
        model.addObject("country", user.getCountry());
        model.addObject("email", user.getEmail());
        return model;
    }
}
