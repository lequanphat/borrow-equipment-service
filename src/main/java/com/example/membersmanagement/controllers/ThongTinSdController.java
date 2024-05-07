package com.example.membersmanagement.controllers;

import com.example.membersmanagement.config.CustomUserDetails;
import com.example.membersmanagement.dtos.BookingDeviceDto;
import com.example.membersmanagement.dtos.ThongTinSd.MuonThietBiDto;
import com.example.membersmanagement.dtos.ThongTinSd.TraThietBiDto;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.dtos.ThongTinSd.VaoKhuVucHocTapDto;
import com.example.membersmanagement.entities.ThongTinSdEntity;
import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.mappers.ThongTinSdMapper;
import com.example.membersmanagement.services.ThanhVienService;
import com.example.membersmanagement.services.ThietBiService;
import com.example.membersmanagement.services.ThongTinSdService;
import com.example.membersmanagement.services.XuLyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class ThongTinSdController {
    @Autowired
    private ThongTinSdService thongTinSdService;
    @Autowired
    private ThanhVienService thanhVienService;
    @Autowired
    private ThietBiService thietBiService;
    @Autowired
    private XuLyService xuLyService;

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

    @GetMapping("/add-booking-device")
    public String addBookingDevice(Model model, Authentication authentication,
                                   @RequestParam(required = false, defaultValue = "") String keyword,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "8") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<ThietBiEntity> list = thietBiService.getAll(keyword, paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagedList", list);
        model.addAttribute("bookingDeviceDTO", BookingDeviceDto.builder().build());
        return "pages/main/add-booking-device";
    }


    @PostMapping("/add-booking-device")
    public String addBookingDevice(@Valid @ModelAttribute("bookingDeviceDTO") BookingDeviceDto bookingDeviceDTO, BindingResult result, Model model, Authentication authentication,
                                   @RequestParam(required = false, defaultValue = "") String keyword,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "8") int size){
        Pageable paging = PageRequest.of(page - 1, size);
        Page<ThietBiEntity> list = thietBiService.getAll(keyword, paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagedList", list);
        model.addAttribute("bookingDeviceDTO", bookingDeviceDTO);
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            log.error(result.getAllErrors().toString());
            return "pages/main/add-booking-device";
        }
        int maTv = ((CustomUserDetails) authentication.getPrincipal()).getMaTV();
        int maTb = bookingDeviceDTO.getMaTB();

        if (!thietBiService.existsByMaTb(maTb)) {
            result.rejectValue("MaTB", "notFound", "Mã thiết bị không tồn tại.");
        }

        if (bookingDeviceDTO.getTGDatCho().isBefore(LocalDateTime.now())) {
            result.rejectValue("TGDatCho", null, "Thời gian đặt chỗ phải lớn hơn hoặc bằng thời gian hiện tại");
        }

        if (thietBiService.isBorrowedOrBooked(maTb)) {
            result.rejectValue("MaTB", "unavailable", "Thiết bị đang được mượn hoặc đặt chỗ");
        }

        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "pages/main/add-booking-device";
        }
        bookingDeviceDTO.setMaTV(maTv);
        thongTinSdService.save(ThongTinSdMapper.toThongTinSdFromBookingDevice(bookingDeviceDTO));
        return "redirect:/my-booking-devices";
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

    @GetMapping("/admin/enter-study-zone")
    public String enterStudyZone(Model model) {
        model.addAttribute("vaoKhuVucHocTapDto", VaoKhuVucHocTapDto.builder().build());
        return "pages/admin/enter-study-zone";
    }

    @PostMapping("/admin/enter-study-zone")
    public String enterStudyZone(@Valid VaoKhuVucHocTapDto vaoKhuVucHocTapDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("vaoKhuVucHocTapDto", vaoKhuVucHocTapDto);
            return "pages/admin/enter-study-zone";
        }

        if (!thanhVienService.existsByMaTV(vaoKhuVucHocTapDto.getMaTV())) {
            result.rejectValue("maTV", "notFound", "Mã thành viên không tồn tại.");
        }

        XuLyEntity xuLyEntity = xuLyService.getXuLyByMaTVAndTrangThai(vaoKhuVucHocTapDto.getMaTV(), 1);
        if (xuLyEntity != null) {
            result.rejectValue("maTV", "unavailable", "Thành viên đang bị vi phạm: " + xuLyEntity.getHinhThucXL());
        }

        if (result.hasErrors()) {
            model.addAttribute("vaoKhuVucHocTapDto", vaoKhuVucHocTapDto);
            return "pages/admin/enter-study-zone";
        }

        ThongTinSdEntity thongTinSdEntity = ThongTinSdMapper.toThongTinSdFromVaoKhuVucHocTapDto(vaoKhuVucHocTapDto);
        thongTinSdService.save(thongTinSdEntity);
        return "redirect:/admin/enter-study-zone";
    }

    @GetMapping("/admin/booking-devices")
    public String bookingDevice(Model model,
                                @RequestParam(required = false, defaultValue = "") String keyword,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "8") int size){
        Pageable paging = PageRequest.of(page - 1, size);
        Page<ThongTinSdEntity> list = thongTinSdService.getDsDatChoThietBi(keyword, paging);
        log.info(list.toString());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagedList", list);
        return "pages/admin/booking-devices";
    }
}
