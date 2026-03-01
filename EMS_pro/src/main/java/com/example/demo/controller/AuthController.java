package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {

        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser != null) {
            return "redirect:/register?error";
        }

        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user,
                            HttpSession session,
                            Model model) {

        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser != null &&
                existingUser.getPassword().equals(user.getPassword())) {

            session.setAttribute("loggedInUser", existingUser);
            return "redirect:/dashboard";
        }

        model.addAttribute("error", "Invalid Email or Password");
        return "auth/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "auth/dashboard";
    }
}