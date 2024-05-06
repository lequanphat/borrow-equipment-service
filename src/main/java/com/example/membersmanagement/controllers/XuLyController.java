package com.example.membersmanagement.controllers;

import com.example.membersmanagement.config.CustomUserDetails;
import com.example.membersmanagement.dtos.XuLy.CreateXuLyDto;
import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.services.XuLyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class XuLyController {
    @Autowired
    private XuLyService xuLyService;

    @GetMapping("/violation")
    public String myViolation(Model model, Authentication authentication) {
        int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
        List<XuLyEntity> list = xuLyService.getXuLyByMaTV(maTv);
        model.addAttribute("list", list);
        return "pages/main/violation";
    }

    @GetMapping("/admin/violation")
    public String admin_violation(Model model) {
        List<XuLyEntity> list = xuLyService.getAll();
        model.addAttribute("list", list);
        return "pages/admin/violation";
    }

    @GetMapping("/admin/add-violation")
    public String addMember(Model model) {
        model.addAttribute("violationsDto", CreateXuLyDto.builder().build());
        return "pages/admin/add-violation";
    }

    @PostMapping("/admin/add-violation")
    public String addMemberProcess(@Valid @ModelAttribute("violationsDto") CreateXuLyDto violationsDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("violationsDto", violationsDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-violation";
        }

        /*if (xuLyService.existsByMaTV(Integer.valueOf(violationsDto.getMaTV()))) {
            result.rejectValue("maTv", "duplicate", "Mã thành viên đã tồn tại.");
            model.addAttribute("violationsDto", violationsDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-violation";
        }

        if (xuLyService.existsByEmail(violationsDto.getEmail()) != null) {
            result.rejectValue("email", "duplicate", "Email này đã được đăng kí với tài khoản khác.");
            model.addAttribute("violationsDto", violationsDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-violation";
        }

        xuLyService.save2(violationsDto);*/
        return "redirect:/admin/violation?success";
    }
}
