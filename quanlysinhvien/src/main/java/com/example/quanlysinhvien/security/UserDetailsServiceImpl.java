package com.example.quanlysinhvien.security;

import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.repository.NhanvienRepository;
import com.example.quanlysinhvien.repository.SinhVienRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SinhVienRepository sinhVienRepository;
    private final NhanvienRepository nhanvienRepository;

    public UserDetailsServiceImpl(SinhVienRepository sinhVienRepository,
            NhanvienRepository nhanvienRepository) {
        this.sinhVienRepository = sinhVienRepository;
        this.nhanvienRepository = nhanvienRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Xác định role dựa theo email
        if (email.contains("student")) {
            SinhVien sv = sinhVienRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy: " + email));
            return new User(
                    sv.getEmail(),
                    sv.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_SINHVIEN")));
        } else if (email.contains("teacher")) {
            Nhanvien nv = nhanvienRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy: " + email));
            return new User(
                    nv.getEmail(),
                    nv.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_GIANGVIEN")));
        } else if (email.contains("admin")) {
            Nhanvien nv = nhanvienRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy: " + email));
            return new User(
                    nv.getEmail(),
                    nv.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        throw new UsernameNotFoundException("Email không hợp lệ: " + email);
    }
}