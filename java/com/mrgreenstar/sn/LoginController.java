package com.mrgreenstar.sn;

import com.mrgreenstar.sn.Entity.User;
import com.mrgreenstar.sn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping({"/","/login"})
    public String loginPage(Principal principal) {
        // Для перенаправления уже залогинненого пользователи со страницы авторизации
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User usr = userRepository.findUserByEmail(principal.getName());
            return "redirect:/user_" + Long.toString(usr.getId());
        }
        return "login";
    }
}
