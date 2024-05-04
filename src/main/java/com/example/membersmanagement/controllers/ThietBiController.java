package com.example.membersmanagement.controllers;

import com.example.membersmanagement.dtos.BookingDeviceDto;
import com.example.membersmanagement.dtos.ThietBi.UpdateThietBiDto;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.mappers.ThietBiMapper;
import com.example.membersmanagement.services.ThietBiService;
import com.example.membersmanagement.dtos.ThietBi.CreateThietBiDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@Slf4j
public class ThietBiController {
    @Autowired
    private ThietBiService thietBiService;

    @GetMapping("/admin/devices")
    public String admin_devices(Model model,
                                @RequestParam(required = false, defaultValue = "") String keyword,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "8") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<ThietBiEntity> list = thietBiService.getAll(keyword, paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagedList", list);
        return "pages/admin/devices";
    }

    @GetMapping("/admin/add-device")
    public String addDevice(Model model) {
        model.addAttribute("deviceDto", CreateThietBiDto.builder().build());
        return "pages/admin/add-device";
    }

    @PostMapping("/admin/add-device")
    public String addDeviceProcess(@Valid @ModelAttribute("deviceDto") CreateThietBiDto deviceDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("deviceDto", deviceDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-device";
        }

        if (thietBiService.existsByMaTb(Integer.valueOf(deviceDto.getMaTB()))) {
            result.rejectValue("maTb", "duplicate", "Mã thiết bị đã tồn tại.");
            model.addAttribute("deviceDto", deviceDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-device";
        }

        thietBiService.save(deviceDto);
        return "redirect:/admin/devices?success";
    }

    @GetMapping("/admin/devices/{id}")
    public String updateDevice(@PathVariable int id, Model model) {
        ThietBiEntity device = thietBiService.getById(id);
        CreateThietBiDto deviceDto = ThietBiMapper.toDto(device);
        model.addAttribute("deviceDto", deviceDto);
        return "pages/admin/update-device";
    }

    @PostMapping("/admin/devices/{id}")
    public String updateDeviceProcess(@PathVariable int id,
                                      @Valid @ModelAttribute("deviceDto") UpdateThietBiDto deviceDto,
                                      BindingResult result,
                                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("deviceDto", deviceDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/update-device";
        }

        thietBiService.update(deviceDto);
        return "redirect:/admin/devices?success";
    }

    @DeleteMapping("/admin/devices/{id}")
    public String deleteDevice(@PathVariable int id, Model model) {
        if (thietBiService.isBorrowedOrBooked(id)) {
            // Thiết bị đã được mượn hoặc đặt chỗ, không thể xóa
            model.addAttribute("errorMessage", "Thiết bị này đã được đặt chỗ, không thể xóa thiết bị.");
        } else {
            // Nếu không có vấn đề gì, tiến hành xóa thiết bị
            thietBiService.delete(id);
        }
        return "redirect:/admin/devices";
    }
}
