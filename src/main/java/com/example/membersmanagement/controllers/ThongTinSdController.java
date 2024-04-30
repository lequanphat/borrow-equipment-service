package com.example.membersmanagement.controllers;

import com.example.membersmanagement.config.CustomUserDetails;
import com.example.membersmanagement.dtos.ThietBi.CreateThietBiDto;
import com.example.membersmanagement.dtos.ThongTinSd.MuonThietBiDto;
import com.example.membersmanagement.dtos.ThongTinSd.TraThietBiDto;
import com.example.membersmanagement.entities.ThongTinSdEntity;
import com.example.membersmanagement.mappers.ThongTinSdMapper;
import com.example.membersmanagement.services.ThanhVienService;
import com.example.membersmanagement.services.ThietBiService;
import com.example.membersmanagement.services.ThongTinSdService;
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
public class ThongTinSdController {
    @Autowired
    private ThongTinSdService thongTinSdService;
    @Autowired
    private ThanhVienService thanhVienService;
    @Autowired
    private ThietBiService thietBiService;

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

    @GetMapping("/admin/device-borrowing")
    public String deviceBorrowing(Model model) {
        model.addAttribute("muonThietBiDto", MuonThietBiDto.builder().build());
        return "pages/admin/device-borrowing";
    }

    @PostMapping("/admin/device-borrowing")
    public String deviceBorrowing(@Valid MuonThietBiDto muonThietBiDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("muonThietBiDto", muonThietBiDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/device-borrowing";
        }

        int maTV = Integer.valueOf(muonThietBiDto.getMaTV());
        int maTB = Integer.valueOf(muonThietBiDto.getMaTB());

        if (!thanhVienService.existsByMaTV(maTV)) {
            result.rejectValue("maTV", "notFound", "Mã thành viên không tồn tại.");
        }

        if (!thietBiService.existsByMaTb(maTB)) {
            result.rejectValue("maTB", "notFound", "Mã thiết bị không tồn tại.");
        }

        if (thietBiService.isBorrowedOrBooked(maTB)) {
            result.rejectValue("maTB", "unavailable", "Thiết bị không thể mượn.");
        }

        if (result.hasErrors()) {
            model.addAttribute("muonThietBiDto", muonThietBiDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/device-borrowing";
        }

        ThongTinSdEntity thongTinSdEntity = ThongTinSdMapper.toThongTinSdFromMuonThietBi(muonThietBiDto);
        thongTinSdService.save(thongTinSdEntity);
        return "redirect:/admin/device-borrowing";
    }

    @GetMapping("/admin/return-device")
    public String returnDevice(Model model) {
        model.addAttribute("traThietBiDto", TraThietBiDto.builder().build());
        return "pages/admin/return-device";
    }

    @PostMapping("/admin/return-device")
    public String returnDevice(@Valid TraThietBiDto traThietBiDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("traThietBiDto", traThietBiDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/return-device";
        }

        if (!thietBiService.existsByMaTb(traThietBiDto.getMaTB())) {
            result.rejectValue("maTB", "notFound", "Mã thiết bị không tồn tại.");
        } else {
            if (!thietBiService.isBorrowedOrBooked(traThietBiDto.getMaTB())) {
                result.rejectValue("maTB", "unavailable", "Thiết bị chưa được mượn.");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("muonThietBiDto", traThietBiDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/return-device";
        }

        thongTinSdService.traThietBi(traThietBiDto.getMaTB());
        return "redirect:/admin/return-device";
    }
}
