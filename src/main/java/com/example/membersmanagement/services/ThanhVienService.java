package com.example.membersmanagement.services;

import com.example.membersmanagement.entities.ThanhVienDTO;
import com.example.membersmanagement.repositories.ThanhVienRepository;
import com.example.membersmanagement.validators.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThanhVienService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ThanhVienRepository thanhVienRepository;

    public ThanhVienService(ThanhVienRepository thanhVienRepository) {
        this.thanhVienRepository = thanhVienRepository;
    }

    public List<ThanhVienDTO> findAll() {
        return thanhVienRepository.findAll();
    }
    public ThanhVienDTO findByEmail(String email) {
        return thanhVienRepository.findByEmail(email);
    }
    public ThanhVienDTO findByMaTV(int maTV) {
        return thanhVienRepository.findByMaTV(maTV);
    }
    public ThanhVienDTO save(RegistrationValidator registerData) {
        ThanhVienDTO thanhVien = new ThanhVienDTO();
        thanhVien.setMaTV(registerData.getMaTv());
        thanhVien.setEmail(registerData.getEmail());
        thanhVien.setHoTen(registerData.getHoTen());
        thanhVien.setPassword(registerData.getPassword());
        return thanhVienRepository.save(thanhVien);
    }
    public ThanhVienDTO register(RegistrationValidator registerData) throws Exception {
        ThanhVienDTO thanhvien = thanhVienRepository.findByEmail(registerData.getEmail());
        if(thanhvien != null) {
            throw new Exception("Email này đã được đăng kí với tài khoản khác.");
        }
        thanhvien = thanhVienRepository.findByMaTV(registerData.getMaTv());
        if(thanhvien != null) {
            throw new Exception("Mã sinh viên đã được đăng kí với tài khoản khác.");
        }
        registerData.setPassword(passwordEncoder.encode(registerData.getPassword()));
        return this.save(registerData);
    }


}
