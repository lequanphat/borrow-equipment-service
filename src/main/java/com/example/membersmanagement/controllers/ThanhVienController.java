package com.example.membersmanagement.controllers;

import com.example.membersmanagement.config.CustomUserDetails;
import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.services.ThanhVienService;
import com.example.membersmanagement.validators.ChangePasswordValidator;
import com.example.membersmanagement.validators.UpdateProfileValidator;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class ThanhVienController {
    private ThanhVienService thanhVienService;

    public ThanhVienController(ThanhVienService thanhVienService) {
        this.thanhVienService = thanhVienService;
    }


    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
        ThanhVienEntity thanhVien = thanhVienService.findByMaTV(maTv);
        model.addAttribute("thanhVien", thanhVien);
        return "pages/main/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute("thanhvien") UpdateProfileValidator data, BindingResult result, Model model, Authentication authentication) {
        model.addAttribute("thanhVien", data);
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "pages/main/profile";
        }
        try {
            int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
            thanhVienService.updateProfile(maTv, data);
            return "redirect:/profile?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pages/main/profile";
        }
    }

    @GetMapping("/change-password")
    public String changePasswordUI() {
        return "pages/main/change-password";
    }


    @PostMapping("/change-password")
    public String changePasswordProcess(@Valid @ModelAttribute("thanhvien") ChangePasswordValidator data, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "pages/main/change-password";
        }
        try {
            int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
            thanhVienService.changePassword(maTv, data);
            return "redirect:/change-password?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pages/main/change-password";
        }
    }
}
