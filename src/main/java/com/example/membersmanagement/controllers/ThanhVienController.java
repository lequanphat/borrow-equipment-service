package com.example.membersmanagement.controllers;

import com.example.membersmanagement.entities.ThanhVienDTO;
import com.example.membersmanagement.services.ThanhVienService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Controller
public class ThanhVienController {
    private ThanhVienService thanhVienService;

    public ThanhVienController(ThanhVienService thanhVienService) {
        this.thanhVienService = thanhVienService;
    }

    @ResponseBody()
    @GetMapping("/members")
    public List<ThanhVienDTO> getAll() {
        return thanhVienService.findAll();
    }
}
