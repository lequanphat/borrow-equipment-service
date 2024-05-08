package com.example.membersmanagement.config;

import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.repositories.ThanhVienRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ThanhVienRepository thanhVienRepository;

    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ThanhVienEntity user = thanhVienRepository.findByEmail(username);
        session.setAttribute("username", username);
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
