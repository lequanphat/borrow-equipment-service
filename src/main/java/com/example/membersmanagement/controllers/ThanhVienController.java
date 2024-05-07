package com.example.membersmanagement.controllers;

import com.example.membersmanagement.config.CustomUserDetails;
import com.example.membersmanagement.dtos.ThanhVien.CreateThanhVienDto;
import com.example.membersmanagement.dtos.ThanhVien.UpdateThanhVienDto;
import com.example.membersmanagement.dtos.ThietBi.CreateThietBiDto;
import com.example.membersmanagement.dtos.ThietBi.UpdateThietBiDto;
import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.mappers.ThanhVienMapper;
import com.example.membersmanagement.services.ThanhVienService;
import com.example.membersmanagement.dtos.ChangePasswordDto;
import com.example.membersmanagement.dtos.UpdateProfileDto;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public String updateProfile(@Valid @ModelAttribute("thanhvien") UpdateProfileDto data, BindingResult result, Model model, Authentication authentication) {
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
    public String changePasswordProcess(@Valid @ModelAttribute("thanhvien") ChangePasswordDto data, BindingResult result, Model model, Authentication authentication) {
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

    @GetMapping("/admin/members")
    public String admin_members(Model model) {
        List<ThanhVienEntity> list = thanhVienService.getAll();
        model.addAttribute("list", list);
        return "pages/admin/members";
    }

    @GetMapping("/admin/add-members")
    public String addMember(Model model) {
        model.addAttribute("membersDto", CreateThanhVienDto.builder().build());
        return "pages/admin/add-members";
    }

    @PostMapping("/admin/add-members")
    public String addMemberProcess(@Valid @ModelAttribute("membersDto") CreateThanhVienDto membersDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("membersDto", membersDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-members";
        }

        if (thanhVienService.existsByMaTV(Integer.valueOf(membersDto.getMaTV()))) {
            result.rejectValue("maTv", "duplicate", "Mã thành viên đã tồn tại.");
            model.addAttribute("membersDto", membersDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-members";
        }

        if (thanhVienService.existsByEmail(membersDto.getEmail()) != null) {
            result.rejectValue("email", "duplicate", "Email này đã được đăng kí với tài khoản khác.");
            model.addAttribute("membersDto", membersDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-members";
        }

        thanhVienService.save2(membersDto);
        return "redirect:/admin/members?success";
    }

    @GetMapping("/admin/members/{id}")
    public String updateMember(@PathVariable int id, Model model) {
        ThanhVienEntity member = thanhVienService.findByMaTV(id);
        CreateThanhVienDto membersDto = ThanhVienMapper.toDto(member);
        model.addAttribute("membersDto", membersDto);
        return "pages/admin/update-members";
    }

    @PostMapping("/admin/members/{id}")
    public String updateMemberProcess(@PathVariable int id,
                                      @Valid @ModelAttribute("membersDto") UpdateThanhVienDto membersDto,
                                      BindingResult result,
                                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("membersDto", membersDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/update-members";
        }

        thanhVienService.update(membersDto);
        return "redirect:/admin/members?success";
    }

    @PostMapping("/admin/members/delete/{id}")
    public String deleteDevice(@PathVariable int id) {
        thanhVienService.delete(id);
        return "redirect:/admin/members?success";
    }
}
