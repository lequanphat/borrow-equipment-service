package com.example.membersmanagement.controllers;

import com.example.membersmanagement.config.CustomUserDetails;
import com.example.membersmanagement.dtos.ThanhVien.CreateThanhVienDto;
import com.example.membersmanagement.dtos.ThanhVien.UpdateThanhVienDto;
import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.ThongTinSdEntity;
import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.mappers.ThanhVienMapper;
import com.example.membersmanagement.services.ThanhVienService;
import com.example.membersmanagement.dtos.ChangePasswordDto;
import com.example.membersmanagement.dtos.UpdateProfileDto;
import com.example.membersmanagement.services.ThongTinSdService;
import com.example.membersmanagement.services.XuLyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ThanhVienController {
    @Autowired
    private ThanhVienService thanhVienService;
    @Autowired

    private ThongTinSdService thongTinSdService;
    @Autowired
    private XuLyService xuLyService;

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
        ThanhVienEntity thanhVien = thanhVienService.findByMaTV(maTv);
        model.addAttribute("thanhVien", thanhVien);
        return "pages/main/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute("thanhvien") UpdateProfileDto data, BindingResult result, Model model, Authentication authentication) {
        model.addAttribute("thanhVien", data);
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "pages/main/profile";
        }
        try {
            int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
            thanhVienService.updateProfile(maTv, data);
            return "redirect:/profile?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pages/main/profile";
        }
    }

    @GetMapping("/change-password")
    public String changePasswordUI() {
        return "pages/main/change-password";
    }

    @PostMapping("/change-password")
    public String changePasswordProcess(@Valid @ModelAttribute("thanhvien") ChangePasswordDto data, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "pages/main/change-password";
        }
        try {
            int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
            thanhVienService.changePassword(maTv, data);
            return "redirect:/change-password?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pages/main/change-password";
        }
    }

    @GetMapping("/admin/members")
    public String admin_members(Model model,
                                @RequestParam(required = false, defaultValue = "") String keyword,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "8") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<ThanhVienEntity> list = thanhVienService.getAll(keyword, paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagedList", list);
        return "pages/admin/members";
    }

    @GetMapping("/admin/add-members")
    public String addMember(Model model) {
        model.addAttribute("membersDto", CreateThanhVienDto.builder().build());
        return "pages/admin/add-members";
    }

    @PostMapping("/admin/add-members")
    public String addMemberProcess(@Valid @ModelAttribute("membersDto") CreateThanhVienDto membersDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("membersDto", membersDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-members";
        }

        if (thanhVienService.existsByMaTV(Integer.valueOf(membersDto.getMaTV()))) {
            result.rejectValue("maTv", "duplicate", "Mã thành viên đã tồn tại.");
            model.addAttribute("membersDto", membersDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-members";
        }

        if (thanhVienService.existsByEmail(membersDto.getEmail()) != null) {
            result.rejectValue("email", "duplicate", "Email này đã được đăng kí với tài khoản khác.");
            model.addAttribute("membersDto", membersDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/add-members";
        }

        thanhVienService.createMember(membersDto);
        return "redirect:/admin/members?success";
    }

    @GetMapping("/admin/members/{id}")
    public String updateMember(@PathVariable int id, Model model) {
        ThanhVienEntity member = thanhVienService.findByMaTV(id);
        CreateThanhVienDto membersDto = ThanhVienMapper.toDto(member);
        model.addAttribute("membersDto", membersDto);
        return "pages/admin/update-members";
    }

    @PostMapping("/admin/members/{id}")
    public String updateMemberProcess(@PathVariable int id,
                                      @Valid @ModelAttribute("membersDto") UpdateThanhVienDto membersDto,
                                      BindingResult result,
                                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("membersDto", membersDto);
            model.addAttribute("errors", result.getAllErrors());
            return "pages/admin/update-members";
        }

        thanhVienService.update(membersDto);
        return "redirect:/admin/members?success";
    }

    @PostMapping("/admin/members/delete/{id}")
    public String deleteMembers(@PathVariable int id) {
        thanhVienService.delete(id);
        return "redirect:/admin/members?success";
    }

    @GetMapping("/admin/detail-member/{id}")
    public String detailMember(@PathVariable int id, Model model) {
        if (thanhVienService.existsByMaTV(id) == false) {
            return "pages/error/404";
        }
        ThanhVienEntity member = thanhVienService.findByMaTV(id);
        List<ThongTinSdEntity> lichSuMuon = thongTinSdService.getThietBiDangMuonByMaTV(id);
        List<ThongTinSdEntity> lichSuVaoKv = thongTinSdService.getLichSuVaoKVHocTap(id);
        List<XuLyEntity> xuLy = xuLyService.getXuLyByMaTV(id);
        model.addAttribute("memberInfo", member);
        model.addAttribute("lichSuMuon", lichSuMuon);
        model.addAttribute("lichSuVaoKv", lichSuVaoKv);
        model.addAttribute("xuLy", xuLy);
        return "pages/admin/detail-member";
    }

    @PostMapping("/admin/members/delete-multiple")
    public String deleteMultiMembers(@RequestParam("khoa") int khoa) {
        thanhVienService.multipleDelete(khoa);
        return "redirect:/admin/members?success";
    }

}
