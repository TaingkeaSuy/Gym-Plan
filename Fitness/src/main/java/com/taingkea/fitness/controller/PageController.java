package com.taingkea.fitness.controller;

import com.taingkea.fitness.model.User;
import com.taingkea.fitness.service.SubscriptionService;
import com.taingkea.fitness.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    /* ── LANDING ── */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /* ── REGISTER ── */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Please fill in all fields.");
            return "register";
        }
        if (password.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters.");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }
        if (userService.existsByEmail(email)) {
            model.addAttribute("error", "Email already registered.");
            return "register";
        }

        userService.register(name, email, password);
        redirectAttributes.addFlashAttribute("success", "Account created! Please sign in.");
        return "redirect:/login";
    }

    /* ── LOGIN ── */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userService.login(email, password);

        if (user == null) {
            model.addAttribute("error", "Wrong email or password.");
            return "login";
        }

        session.setAttribute("user", user);
        return "redirect:/dashboard";
    }

    /* ── LOGOUT ── */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /* ── DASHBOARD ── */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("subscription", subscriptionService.getLatestSubscription(user.getId()));
        return "dashboard";
    }

    /* ── SUBSCRIBE ── */
    @PostMapping("/subscribe")
    public String subscribe(@RequestParam String plan,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        subscriptionService.subscribe(user.getId(), plan);
        redirectAttributes.addFlashAttribute("success", "Membership activated! 💪");
        return "redirect:/dashboard";
    }
}