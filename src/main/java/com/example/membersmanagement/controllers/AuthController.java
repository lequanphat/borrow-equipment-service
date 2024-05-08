package com.example.membersmanagement.controllers;

import com.example.membersmanagement.services.ThanhVienService;
import com.example.membersmanagement.dtos.ForgotPasswordDto;
import com.example.membersmanagement.dtos.RegistrationDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@Controller
public class AuthController {
    @Autowired
    private ThanhVienService thanhVienService;
    @Autowired
    private HttpSession session;

    public AuthController(ThanhVienService thanhVienService) {
        this.thanhVienService = thanhVienService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        String username = (String) session.getAttribute("username");
        String loginError = (String) session.getAttribute("loginError");
        model.addAttribute("username", username);
        model.addAttribute("loginError", loginError);
        return "pages/auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("thanhvien", new RegistrationDto());
        List<Set<String>> list = thanhVienService.getDanhSachNganhAndKhoa();
        model.addAttribute("danhSachNganh", list.get(0));
        model.addAttribute("danhSachKhoa", list.get(1));
        return "pages/auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("thanhvien") RegistrationDto user, BindingResult result, Model model) {
        List<Set<String>> list = thanhVienService.getDanhSachNganhAndKhoa();
        model.addAttribute("danhSachNganh", list.get(0));
        model.addAttribute("danhSachKhoa", list.get(1));
        if (result.hasErrors()) {
            model.addAttribute("registerError", result.getAllErrors().getLast().getDefaultMessage());
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
    public String forgotPasswordProcess(@Valid @ModelAttribute("thanhvien") ForgotPasswordDto data, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors().getLast().getDefaultMessage());
            return "pages/auth/forgot-password";
        }
        try {
            thanhVienService.sendPassword(data.getEmail());
            return "redirect:/check-email";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pages/auth/forgot-password";
        }
    }

    @GetMapping("/check-email")
    public String checkEmailUI() {
        return "pages/auth/check-email";
    }
}
