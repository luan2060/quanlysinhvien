package com.example.quanlysinhvien.Controller;

import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.Service.ThoiKhoaBieuService;
import com.example.quanlysinhvien.repository.SinhVienRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class ThoiKhoaBieuController {

    private final ThoiKhoaBieuService thoiKhoaBieuService;
    private final SinhVienRepository sinhVienRepo; // ← thêm dòng này

    public ThoiKhoaBieuController(ThoiKhoaBieuService thoiKhoaBieuService,
            SinhVienRepository sinhVienRepo) { // ← thêm tham số
        this.thoiKhoaBieuService = thoiKhoaBieuService;
        this.sinhVienRepo = sinhVienRepo; // ← thêm dòng này
    }

    @GetMapping("/sinhvien/lichhoc")
    public String thoiKhoaBieu(
            @RequestParam(defaultValue = "1") Integer hocKy,
            @RequestParam(defaultValue = "2025-2026") String namHoc,
            Model model,
            Principal principal) {

        String email = principal.getName();
        SinhVien sv = sinhVienRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

        Map<Integer, List<LopHocPhan>> lichHoc = thoiKhoaBieuService.getLichHocBySinhVien((long) sv.getId(), hocKy,
                namHoc);

        int dow = LocalDate.now().getDayOfWeek().getValue();
        int ngayHienTai = (dow == 7) ? 1 : dow + 1;

        model.addAttribute("lichHoc", lichHoc);
        model.addAttribute("ngayHienTai", ngayHienTai);
        model.addAttribute("hocKy", hocKy);
        model.addAttribute("namHoc", namHoc);
        model.addAttribute("sinhVien", sv);

        return "Sinhvien/LichHoc";
    }
}