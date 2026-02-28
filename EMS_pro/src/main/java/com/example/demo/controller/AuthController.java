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
    // =========================
    // REGISTER PAGE
    // =========================
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // =========================
    // SAVE USER
    // =========================
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {

        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser != null) {
            return "redirect:/register?error";
        }

        userService.saveUser(user);

        return "redirect:/login";
    }

    // =========================
    // LOGIN PAGE
    // =========================
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user,
                            Model model,
                            HttpSession session) {

        User existingUser = userService
                .findByEmail(user.getEmail());

        if (existingUser != null &&
            existingUser.getPassword().equals(user.getPassword())) {

            // store user in session
            session.setAttribute("loggedInUser", existingUser);

            return "redirect:/employees"; 
        }

        model.addAttribute("error", "Invalid Email or Password!");
        return "auth/login";
    }
    @GetMapping("/list")
    public String listEmployees(Model model) {
        return "employee/list";
    }
}