package com.example.membersmanagement.controllers;

import com.example.membersmanagement.dtos.ThongKe.ThongKeLuotVaoDto;
import com.example.membersmanagement.dtos.ThongKe.ThongKeMuonThietBiDto;
import com.example.membersmanagement.entities.ThongTinSdEntity;
import com.example.membersmanagement.services.ThongTinSdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
public class ThongKeController {
    @Autowired
    private ThongTinSdService thongTinSdService;

    @GetMapping("/admin/statistics-of-visits")
    public String statisticsOfVisits(@RequestParam(name = "thongKeTheo", required = false, defaultValue = "khoa") String thongKeTheo,
                                     @RequestParam(name = "tgBatDau", required = false, defaultValue = "01/01/1990") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate tgBatDau,
                                     @RequestParam(name = "tgKetThuc", required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate tgKetThuc,
                                     Model model) {
        List<ThongKeLuotVaoDto> list = thongTinSdService.thongKeLuotVao(thongKeTheo, tgBatDau, tgKetThuc);
        model.addAttribute("list", list);
        model.addAttribute("thongKeTheo", thongKeTheo);
        model.addAttribute("tgBatDau", tgBatDau);
        model.addAttribute("tgKetThuc", tgKetThuc);
        return "pages/admin/statistics-of-visits";
    }

    @GetMapping("/admin/manage-borrowed-devices")
    public String manageBorrowedDevices(@RequestParam(name = "search", required = false, defaultValue = "") String search, Model model) {
        List<ThongTinSdEntity> list = thongTinSdService.getDsThietBiDangDuocMuon(search);
        model.addAttribute("list", list);
        model.addAttribute("search", search);
        return "pages/admin/manage-borrowed-devices";
    }

    @GetMapping("/admin/statistics-borrow")
    public String statisticsOfBorrow(@RequestParam(name = "tgBatDau", required = false, defaultValue = "01/01/1990") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate tgBatDau,
                                     @RequestParam(name = "tgKetThuc", required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate tgKetThuc,
                                     Model model) {
        List<ThongKeMuonThietBiDto> list = thongTinSdService.thongKeMuonThietBiTheoNgay(tgBatDau, tgKetThuc);
        model.addAttribute("list", list);
        model.addAttribute("tgBatDau", tgBatDau);
        model.addAttribute("tgKetThuc", tgKetThuc);
        return "pages/admin/statistics-borrow";
    }
}
