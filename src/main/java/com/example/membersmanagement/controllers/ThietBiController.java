package com.example.membersmanagement.controllers;

import com.example.membersmanagement.dtos.ThietBi.UpdateThietBiDto;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.mappers.ThietBiMapper;
import com.example.membersmanagement.services.ThietBiService;
import com.example.membersmanagement.dtos.ThietBi.CreateThietBiDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class ThietBiController {
    @Autowired
    private ThietBiService thietBiService;

    @GetMapping("/devices")
    public String devices(Model model) {
        List<ThietBiEntity> list = thietBiService.getAll();
        model.addAttribute("list", list);
        return "pages/main/devices";
    }

    @GetMapping("/add-device")
    public String addDevice(Model model) {
        model.addAttribute("deviceDto", CreateThietBiDto.builder().build());
        return "pages/main/add-device";
    }

    @PostMapping("/add-device")
    public String addDeviceProcess(@Valid @ModelAttribute("deviceDto") CreateThietBiDto deviceDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("deviceDto", deviceDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/main/add-device";
        }

        if (thietBiService.existsByMaTb(deviceDto.getMaTB())) {
            result.rejectValue("maTb", "duplicate", "Mã thiết bị đã tồn tại.");
            model.addAttribute("deviceDto", deviceDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/main/add-device";
        }

        thietBiService.save(deviceDto);
        return "redirect:/devices?success";
    }

    @GetMapping("/devices/{id}")
    public String updateDevice(@PathVariable int id, Model model) {
        ThietBiEntity device = thietBiService.getById(id);
        CreateThietBiDto deviceDto = ThietBiMapper.toDto(device);
        model.addAttribute("deviceDto", deviceDto);
        return "pages/main/update-device";
    }

    @PostMapping("/devices/{id}")
    public String updateDeviceProcess(@PathVariable int id,
                                      @Valid @ModelAttribute("deviceDto") UpdateThietBiDto deviceDto,
                                      BindingResult result,
                                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("deviceDto", deviceDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/main/update-device";
        }

        thietBiService.update(deviceDto);
        return "redirect:/devices?success";
    }
}
