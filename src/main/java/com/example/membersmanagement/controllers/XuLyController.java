package com.example.membersmanagement.controllers;

import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.services.XuLyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class XuLyController {
    @Autowired
    private XuLyService xuLyService;


    @GetMapping("/violation")
    public String usageInformation(Model model) {
        List<XuLyEntity> list = xuLyService.getAll();
        model.addAttribute("list", list);
        return "pages/main/violation";
    }
}
