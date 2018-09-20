package com.mrgreenstar.sn;

import com.mrgreenstar.sn.Entity.Post;
import com.mrgreenstar.sn.Entity.Subscriptions;
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
import java.util.*;

@Controller
public class MainPageController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubscriptionsRepository subscriptionsRepository;

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

    @GetMapping("/redirectToProfile")
    public ModelAndView mainPage(Principal principal, ModelAndView model) {
        User usr = userRepository.findUserByEmail(principal.getName());
        model.setViewName("redirect:/user_" + Long.toString(usr.getId()));
        return model;
    }

    @GetMapping("/user_{userId}")
    public ModelAndView getProfile(@PathVariable("userId") Long userId,
                                   Principal principal, ModelAndView model) {
        User user = userRepository.findUserById(userId);
        User owner = userRepository.findUserByEmail(principal.getName());
        ArrayList<Post> posts = postRepository.findPostsByUserId(user.getId());
        // Получение всех постов
        Set<Subscriptions> subs = owner.getSubscriptions();
        for (Subscriptions sub : subs) {
            User s = userRepository.findUserById(sub.getSubId());
            posts.addAll(s.getPosts());
        }
        // Сортировка по дате
        SortedSet<Post> treeSet = new TreeSet<>(posts);
        model.addObject("ownerId", owner.getId());
        model.addObject("viewId", userId);
        model.addObject("user", user);
        model.addObject("posts", treeSet);
        model.setViewName("main");
        return model;
    }

    // Контроллер, отвечающий за оформление подписок. Нужно убрать "дублирование" подписок
    @PostMapping(value = "/user_{userId}", params = {"subscribe"})
    public ModelAndView subsProfile(@PathVariable("userId") Long userId,
                                    Principal principal, ModelAndView model) {
        // userId - id человека, на которого подписались
        User usr = userRepository.findUserByEmail(principal.getName());
        Subscriptions subs = new Subscriptions(usr, userId);
        subscriptionsRepository.save(subs);
        model.setViewName("redirect:/user_" + userId);
        return model;
    }

    // Контроллер, отвечающий за написание постов
    @PostMapping(value = "/user_{userId}", params = {"post"})
    public ModelAndView postProfile(@PathVariable("userId") Long userId,
                                    @RequestParam String textPost,
                                    Principal principal,
                                    ModelAndView model) {
        User owner = userRepository.findUserByEmail(principal.getName());
        Date date = new Date();
        Post pst = new Post(date, textPost);
        pst.setUser(owner);
        postRepository.save(pst);
        model.setViewName("redirect:/user_" + Long.toString(owner.getId()));
        return model;
    }
}
