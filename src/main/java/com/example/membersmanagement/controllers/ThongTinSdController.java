package com.example.membersmanagement.controllers;

import com.example.membersmanagement.entities.ThongTinSdEntity;
import com.example.membersmanagement.services.ThongTinSdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ThongTinSdController {
    @Autowired
    private ThongTinSdService thongTinSdService;


    @GetMapping("/usage-information")
    public String usageInformation(Model model) {
        List<ThongTinSdEntity> list = thongTinSdService.getAll();
        model.addAttribute("list", list);
        return "pages/main/usage-information";
    }
}
