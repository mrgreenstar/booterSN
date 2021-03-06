package com.mrgreenstar.sn;

import com.mrgreenstar.sn.Entity.Subscriptions;
import com.mrgreenstar.sn.Entity.User;
import com.mrgreenstar.sn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

// Контроллер, отвечающий за отображение списка подписок
@Controller
public class UserSubscriptionsController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/subs")
    public ModelAndView subsPage(Principal principal, ModelAndView model) {
        User usr = userRepository.findUserByEmail(principal.getName());
        Set<Subscriptions> subs = usr.getSubscriptions();
        List<User> userSubs = new ArrayList<>();
        if (subs.isEmpty()) {
            model.addObject("error", "You have no subs");
        }
        for (Subscriptions sub : subs) {
            User s = userRepository.findUserById(sub.getSubId());
            userSubs.add(s);
        }
        model.addObject("userSubs", userSubs);
        model.addObject("ownerId", usr.getId());
        model.setViewName("subs");
        return model;
    }
}
