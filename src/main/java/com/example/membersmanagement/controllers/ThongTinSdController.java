package com.example.membersmanagement.controllers;

import com.example.membersmanagement.config.CustomUserDetails;
import com.example.membersmanagement.entities.ThongTinSdEntity;
import com.example.membersmanagement.services.ThongTinSdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ThongTinSdController {
    @Autowired
    private ThongTinSdService thongTinSdService;

    @GetMapping("/my-borrow-devices")
    public String usageInformation(Model model, Authentication authentication) {
        int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
        List<ThongTinSdEntity> list = thongTinSdService.getThietBiDangMuonByMaTV(maTv);
        model.addAttribute("list", list);
        return "pages/main/borrow-devices";
    }

    @GetMapping("/my-booking-devices")
    public String myBookingInformation(Model model, Authentication authentication) {
        int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
        List<ThongTinSdEntity> list = thongTinSdService.getThietBiDangDatChoByMaTV(maTv);
        model.addAttribute("list", list);
        return "pages/main/booking-devices";
    }

}
