package com.example.membersmanagement.services;

import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.mappers.ThanhVienMapper;
import com.example.membersmanagement.repositories.ThanhVienRepository;
import com.example.membersmanagement.dtos.ChangePasswordDto;
import com.example.membersmanagement.dtos.RegistrationDto;
import com.example.membersmanagement.dtos.UpdateProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThanhVienService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private ThanhVienRepository thanhVienRepository;

    public ThanhVienService(ThanhVienRepository thanhVienRepository) {
        this.thanhVienRepository = thanhVienRepository;
    }

    public List<ThanhVienEntity> getAll() {
        return thanhVienRepository.findAll();
    }

    public ThanhVienEntity findByEmail(String email) {
        return thanhVienRepository.findByEmail(email);
    }

    public ThanhVienEntity findByMaTV(int maTV) {
        return thanhVienRepository.findByMaTV(maTV);
    }

    public ThanhVienEntity save(RegistrationDto registerData) {
        ThanhVienEntity thanhVien = ThanhVienMapper.toThanhVienFromRegistration(registerData);
        return thanhVienRepository.save(thanhVien);
    }

    public ThanhVienEntity register(RegistrationDto registerData) throws Exception {
        ThanhVienEntity thanhvien = thanhVienRepository.findByEmail(registerData.getEmail());
        if (thanhvien != null) {
            throw new Exception("Email này đã được đăng kí với tài khoản khác.");
        }
        thanhvien = thanhVienRepository.findByMaTV(Integer.parseInt(registerData.getMaTv()));
        if (thanhvien != null) {
            throw new Exception("Mã sinh viên đã được đăng kí với tài khoản khác.");
        }
        registerData.setPassword(registerData.getPassword());
        return this.save(registerData);
    }

    public void sendPassword(String email) throws Exception {
        ThanhVienEntity thanhvien = thanhVienRepository.findByEmail(email);
        if (thanhvien == null) {
            throw new Exception("Email này không tồn tại trong hệ thống.");
        }
        emailService.sendSimpleMessage(email, "Send Password", "Mật khẩu của bạn là " + thanhvien.getPassword());
    }

    public ThanhVienEntity updateProfile(int maTv, UpdateProfileDto data) throws Exception {
        try {
            ThanhVienEntity thanhVien = thanhVienRepository.findByMaTV(maTv);
            thanhVien.setHoTen(data.getHoTen());
            thanhVien.setSdt(data.getSdt());
            thanhVien.setKhoa(data.getKhoa());
            thanhVien.setNganh(data.getNganh());
            return thanhVienRepository.save(thanhVien);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public ThanhVienEntity changePassword(int maTv, ChangePasswordDto data) throws Exception {
        try {
            ThanhVienEntity thanhVien = thanhVienRepository.findByMaTV(maTv);
            if (!data.isNewPasswordConfirmed()) {
                throw new Exception("Mật khẩu xác nhận không trùng.");
            }
            if (!thanhVien.getPassword().equals(data.getOldPassword())) {
                throw new Exception("Mật khẩu cũ không đúng.");
            }
            if (thanhVien.getPassword().equals(data.getNewPassword())) {
                throw new Exception("Mật khẩu mới phải khác mật khẩu cũ.");
            }
            thanhVien.setPassword(data.getNewPassword());
            return thanhVienRepository.save(thanhVien);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean existsByMaTV(int maTV) {
        return thanhVienRepository.existsByMaTV(maTV);
    }
}
