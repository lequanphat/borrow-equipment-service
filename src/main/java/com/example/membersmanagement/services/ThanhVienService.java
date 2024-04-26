package com.example.membersmanagement.services;

import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.repositories.ThanhVienRepository;
import com.example.membersmanagement.validators.ChangePasswordValidator;
import com.example.membersmanagement.validators.RegistrationValidator;
import com.example.membersmanagement.validators.UpdateProfileValidator;
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

    public List<ThanhVienEntity> findAll() {
        return thanhVienRepository.findAll();
    }

    public ThanhVienEntity findByEmail(String email) {
        return thanhVienRepository.findByEmail(email);
    }

    public ThanhVienEntity findByMaTV(int maTV) {
        return thanhVienRepository.findByMaTV(maTV);
    }

    public ThanhVienEntity save(RegistrationValidator registerData) {
        ThanhVienEntity thanhVien = new ThanhVienEntity();
        thanhVien.setMaTV(Integer.parseInt(registerData.getMaTv()));
        thanhVien.setEmail(registerData.getEmail());
        thanhVien.setHoTen(registerData.getHoTen());
        thanhVien.setPassword(registerData.getPassword());
        return thanhVienRepository.save(thanhVien);
    }

    public ThanhVienEntity register(RegistrationValidator registerData) throws Exception {
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

    public ThanhVienEntity updateProfile(int maTv, UpdateProfileValidator data) throws Exception {
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

    public ThanhVienEntity changePassword(int maTv, ChangePasswordValidator data) throws Exception {
        try {
            ThanhVienEntity thanhVien = thanhVienRepository.findByMaTV(maTv);
            if (data.getPassword().equals(data.getNewPassword())) {
                throw new Exception("Mật khẩu mới phải khác mật khẩu cũ.");
            }
            if (thanhVien.getPassword().equals(data.getPassword())) {
                thanhVien.setPassword(data.getNewPassword());
                return thanhVienRepository.save(thanhVien);
            }
            throw new Exception("Xác nhận mật khẩu không đúng.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
