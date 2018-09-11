package com.mrgreenstar.sn;

import com.mrgreenstar.sn.Entity.Post;
import com.mrgreenstar.sn.Entity.User;
import com.mrgreenstar.sn.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class MainPageController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping({"/","/login"})
    public String loginPage(Principal principal) {
        // Для перенаправления уже залогинненого пользователи со страницы авторизации
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User usr = userRepository.findByEmail(principal.getName());
            return "redirect:/user_" + Integer.toString(usr.getId());
        }
        return "login";
    }

    @GetMapping("/redirectToProfile")
    public ModelAndView mainPage(Principal principal, ModelAndView model) {
        User usr = userRepository.findByEmail(principal.getName());
        model.setViewName("redirect:/user_" + Integer.toString(usr.getId()));
        return model;
    }

    @GetMapping("/user_{userId}")
    public ModelAndView getProfile(@PathVariable("userId") int userId,
                                   Principal principal, ModelAndView model) {
        User user = userRepository.findById(userId);
        User owner = userRepository.findByEmail(principal.getName());
        ArrayList<Post> posts = new ArrayList<>();
        posts = postRepository.findPostsByUserId(user.getId());
        model.addObject("ownerId", owner.getId());
        model.addObject("viewId", Integer.toString(userId));
        model.addObject("fullName", user.getFirstName() + " " + user.getLastName());
        model.addObject("posts", posts);
        model.setViewName("main");
        return model;
    }

    @PostMapping("/user_{userId}")
    public ModelAndView postProfile(@PathVariable("userId") int userId,
                                    @RequestParam String textPost,
                                    Principal principal,
                                    ModelAndView model) {
        model.addObject("textPost", textPost);
        User owner = userRepository.findByEmail(principal.getName());
        model.addObject("ownerId", owner.getId());
        Date date = new Date();
        Post pst = new Post(date.toString(), textPost);
        pst.setUser(owner);
        postRepository.save(pst);
        model.setViewName("main");
        return model;
    }
}
