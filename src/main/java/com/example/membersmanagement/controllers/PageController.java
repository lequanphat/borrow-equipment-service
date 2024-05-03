package com.example.membersmanagement.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
    @GetMapping("/admin")
    public String admin() {
        return "redirect:/admin/devices";
    }
}
