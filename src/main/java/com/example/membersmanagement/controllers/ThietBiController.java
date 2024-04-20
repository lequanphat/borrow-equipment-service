package com.example.membersmanagement.controllers;

import com.example.membersmanagement.entities.ThietBiDTO;
import com.example.membersmanagement.services.ThietBiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ThietBiController {
    @Autowired
    private ThietBiService thietBiService;

    @GetMapping("/devices")
    public String devices(Model model) {
        List<ThietBiDTO> list = thietBiService.getAll();
        model.addAttribute("list", list);
        return "pages/main/devices";
    }
}
