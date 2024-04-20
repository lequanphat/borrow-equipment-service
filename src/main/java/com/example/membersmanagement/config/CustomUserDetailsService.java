package com.example.membersmanagement.config;

import com.example.membersmanagement.entities.ThanhVienDTO;
import com.example.membersmanagement.repositories.ThanhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ThanhVienRepository thanhVienRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ThanhVienDTO user = thanhVienRepository.findByEmail(username);
        if (user == null) {
            try {
                user = thanhVienRepository.findByMaTV(Integer.parseInt(username));
                if (user != null) {
                    CustomUserDetails customUserDetails = new CustomUserDetails(user);
                    return customUserDetails;
                }
            } catch (Exception e) {
                throw new UsernameNotFoundException("User not found");
            }
        } else {
            CustomUserDetails customUserDetails = new CustomUserDetails(user);
            return customUserDetails;
        }
        return null;
    }
}
