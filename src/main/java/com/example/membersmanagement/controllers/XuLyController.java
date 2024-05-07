package com.example.membersmanagement.controllers;

import com.example.membersmanagement.config.CustomUserDetails;
import com.example.membersmanagement.dtos.ThongKe.ThongKeLuotVaoDto;
import com.example.membersmanagement.dtos.XuLy.CreateXuLyDto;
import com.example.membersmanagement.dtos.XuLy.UpdateXuLyDto;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.mappers.XuLyMapper;
import com.example.membersmanagement.services.XuLyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class XuLyController {
    @Autowired
    private XuLyService xuLyService;

    @GetMapping("/violation")
    public String myViolation(Model model, Authentication authentication) {
        int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
        List<XuLyEntity> list = xuLyService.getXuLyByMaTV(maTv);
        model.addAttribute("list", list);
        return "pages/main/violation";
    }

    @GetMapping("/admin/violation")
    public String admin_violation(Model model,
                                  @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                                  @RequestParam(name = "thongKeTheo", required = false, defaultValue = "3") int thongKeTheo,
                                  @RequestParam(name = "tgBatDau", required = false, defaultValue = "#{T(java.time.LocalDate).now().minusYears(1)}") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate tgBatDau,
                                  @RequestParam(name = "tgKetThuc", required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate tgKetThuc,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "8") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<XuLyEntity> list = xuLyService.filterViolations(keyword, thongKeTheo, tgBatDau, tgKetThuc, paging);
//        int tongTien = xuLyService.getTongTien(list);
        int tongTien = 1000;
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("thongKeTheo", thongKeTheo);
        model.addAttribute("tgBatDau", tgBatDau);
        model.addAttribute("tgKetThuc", tgKetThuc);
        model.addAttribute("pagedList", list);
        model.addAttribute("tongTien", "Tổng tiền : " + tongTien + " VNĐ");
        return "pages/admin/violation";
    }

    @GetMapping("/admin/add-violation")
    public String addMember(Model model) {
        model.addAttribute("violationsDto", CreateXuLyDto.builder().build());
        return "pages/admin/add-violation";
    }

    @PostMapping("/admin/add-violation")
    public String addMemberProcess(@Valid @ModelAttribute("violationsDto") CreateXuLyDto violationsDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("violationsDto", violationsDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-violation";
        }
        xuLyService.save(violationsDto);
        return "redirect:/admin/violation?success";
    }

    @GetMapping("/admin/violation/{id}")
    public String updateDevice(@PathVariable int id, Model model) {
        XuLyEntity xuLy = xuLyService.getById(id);
        UpdateXuLyDto xuLyDto = XuLyMapper.toDto(xuLy);
        model.addAttribute("xuLyDto", xuLyDto);
        return "pages/admin/update-violation";
    }

    @PostMapping("/admin/violation/{id}")
    public String updateViolationProcess(@PathVariable int id,
                                         @Valid @ModelAttribute("violationDto") UpdateXuLyDto xuLyDto,
                                         BindingResult result,
                                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("xuLyDto", xuLyDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/update-violation";
        }

        xuLyService.update(xuLyDto);
        return "redirect:/admin/violation?success";
    }

    @DeleteMapping("/admin/violation/{id}")
    public String deleteViolation(@PathVariable int id, Model model) {
        xuLyService.delete(id);
        return "redirect:/admin/violation";
    }

}
