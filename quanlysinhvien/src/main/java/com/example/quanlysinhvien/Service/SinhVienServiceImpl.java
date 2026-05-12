package com.example.quanlysinhvien.Service;

import org.springframework.stereotype.Service;

import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.Model.UpdateProfileDTO;
import com.example.quanlysinhvien.repository.SinhVienRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.transaction.Transactional;

@Service
public class SinhVienServiceImpl implements SinhVienIFService {

    private final SinhVienRepository sinhVienRepository;

    // ===== Constructor Injection (thay cho @Autowired) =====
    public SinhVienServiceImpl(SinhVienRepository sinhVienRepository) {
        this.sinhVienRepository = sinhVienRepository;
    }

    @Override
    public SinhVien getCurrentSinhVien() {
        // Lấy email từ Spring Security (email được dùng làm username khi login)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return sinhVienRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên: " + email));
    }

    @Override
    @Transactional
    public void updateProfile(UpdateProfileDTO dto) {
        SinhVien sv = getCurrentSinhVien();

        // Chỉ được phép cập nhật 2 trường này
        sv.setSdt(dto.getSdt());
        sv.setDiaChi(dto.getDiaChi());

        sinhVienRepository.save(sv);
    }
}
