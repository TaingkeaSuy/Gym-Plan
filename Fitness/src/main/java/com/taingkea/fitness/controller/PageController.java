package com.taingkea.fitness.controller;

import com.taingkea.fitness.model.Subscription;
import com.taingkea.fitness.model.User;
import com.taingkea.fitness.service.SubscriptionService;
import com.taingkea.fitness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    // ── Home ─────────────────────────────────────────────
//    @GetMapping("/")
//    public String home() {
//        return "index";
//    }

    // ── Login ────────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error  != null) model.addAttribute("error",   "Invalid username or password.");
        if (logout != null) model.addAttribute("message", "You have been logged out.");
        return "login";
    }

    // ── Register ─────────────────────────────────────────
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           RedirectAttributes ra) {
        try {
            userService.register(username, email, password);
            ra.addFlashAttribute("message", "Account created! Please log in.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/register";
        }
    }

    // ── Dashboard ────────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails principal, Model model) {
        User user = userService.findByUsername(principal.getUsername()).orElseThrow();
        Optional<Subscription> activeSub = subscriptionService.getActiveSubscription(user);

        model.addAttribute("user",       user);
        model.addAttribute("activeSub",  activeSub.orElse(null));
        model.addAttribute("subHistory", subscriptionService.getAllForUser(user));
        return "dashboard";
    }

    // ── Subscribe ────────────────────────────────────────
    @PostMapping("/subscribe")
    public String subscribe(@AuthenticationPrincipal UserDetails principal,
                            @RequestParam String planName,
                            @RequestParam String period,
                            RedirectAttributes ra) {
        User user = userService.findByUsername(principal.getUsername()).orElseThrow();
        subscriptionService.subscribe(user, planName, period);
        ra.addFlashAttribute("message", "🎉 You're now on the " + planName + " plan!");
        return "redirect:/dashboard";
    }

    // ── Cancel ───────────────────────────────────────────
    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id, RedirectAttributes ra) {
        subscriptionService.cancel(id);
        ra.addFlashAttribute("message", "Subscription cancelled.");
        return "redirect:/dashboard";
    }
}