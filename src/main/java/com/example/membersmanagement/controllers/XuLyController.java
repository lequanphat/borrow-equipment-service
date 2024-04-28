package com.example.membersmanagement.controllers;

import com.example.membersmanagement.config.CustomUserDetails;
import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.services.XuLyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
