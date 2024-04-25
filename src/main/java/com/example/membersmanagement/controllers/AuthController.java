package com.example.membersmanagement.controllers;

import com.example.membersmanagement.services.ThanhVienService;
import com.example.membersmanagement.validators.ForgotPasswordValidator;
import com.example.membersmanagement.validators.RegistrationValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {
    @Autowired
    private ThanhVienService thanhVienService;

    public AuthController(ThanhVienService thanhVienService) {
        this.thanhVienService = thanhVienService;
    }

    @GetMapping("/login")
    public String login() {
        return "pages/auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("thanhvien", new RegistrationValidator());
        return "pages/auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("thanhvien") RegistrationValidator user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registerError", result.getAllErrors().get(0).getDefaultMessage());
            return "pages/auth/register";
        }
        try {
            thanhVienService.register(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("registerError", e.getMessage());
            return "pages/auth/register";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "pages/auth/forgot-password";
    }

    @PostMapping("/send-password")
    public String forgotPasswordProcess(@Valid @ModelAttribute("thanhvien") ForgotPasswordValidator data, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "pages/auth/forgot-password";
        }
        try {
            thanhVienService.sendPassword(data.getEmail());
            return "pages/auth/send-password";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pages/auth/forgot-password";
        }
    }
}
