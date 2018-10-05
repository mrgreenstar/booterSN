package com.mrgreenstar.sn;

import com.mrgreenstar.sn.Entity.Post;
import com.mrgreenstar.sn.Entity.Subscriptions;
import com.mrgreenstar.sn.Entity.User;
import com.mrgreenstar.sn.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

@Controller
public class FeedController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubscriptionsRepository subscriptionsRepository;

    @GetMapping("/user_{userId}")
    public ModelAndView getProfile(@PathVariable("userId") Long userId,
                                   Principal principal, ModelAndView model) {
        // ownerId - id пользователя, который просматривает
        // userId - id просматриваемого пользователя
        User user = userRepository.findUserById(userId);
        User owner = userRepository.findUserByEmail(principal.getName());
        ArrayList<Post> posts = postRepository.findPostsByUserId(user.getId());
        // Get all post
        if (posts != null) {
            Set<Subscriptions> subs = owner.getSubscriptions();
            for (Subscriptions sub : subs) {
                User s = userRepository.findUserById(sub.getSubId());
                posts.addAll(s.getPosts());
            }
            // Sort posts by date
            SortedSet<Post> treeSet = new TreeSet<>(posts);
            model.addObject("posts", treeSet);
        }
        model.addObject("ownerId", owner.getId());
        model.addObject("viewId", userId);
        model.addObject("user", user);
        // If owner is already subscribed on user(whose page he is looking) so isSubscribed = true
        boolean btnType = subscriptionsRepository.findSubscriptionsByUserAndSubId(owner, userId) != null;
        model.addObject("isSubscribed", btnType);
        model.setViewName("main");
        return model;
    }

    // Контроллер, отвечающий за оформление ПОДписок
    @PostMapping(value = "/user_{userId}", params = {"subscribe"})
    public ModelAndView subsProfile(@PathVariable("userId") Long userId,
                                    Principal principal, ModelAndView model) {
        // userId - id человека, на которого подписались
        User usr = userRepository.findUserByEmail(principal.getName());
        Subscriptions s = subscriptionsRepository.findSubscriptionsByUserAndSubId(usr, userId);
        if (!userId.equals(usr.getId()) && s == null) {
            Subscriptions subs = new Subscriptions(usr, userId);
            subscriptionsRepository.save(subs);
        }
        model.setViewName("redirect:/user_" + userId);
        return model;
    }
    // Контроллер, отвечающий за оформление ОТписок
    @PostMapping(value = "/user_{userId}", params = {"unsubscribe"})
    public ModelAndView unsubProfile(@PathVariable("userId") Long userId, Principal principal,
                                     ModelAndView model) {
        User usr = userRepository.findUserByEmail(principal.getName());
        subscriptionsRepository.deleteSubscriptionsByUserAndSubId(usr.getId(), userId);
        model.setViewName("redirect:/user_" + userId);
        return model;
    }
    // Controller for post messages function
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
