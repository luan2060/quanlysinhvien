package com.example.quanlysinhvien.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.repository.LopHocPhanRepository;
import com.example.quanlysinhvien.repository.NhanvienRepository;

@Service
public class LichGiangDayService {

    private final LopHocPhanRepository lopHocPhanRepo;
    private final NhanvienRepository nhanvienRepo;

    public LichGiangDayService(LopHocPhanRepository lopHocPhanRepo,
            NhanvienRepository nhanvienRepo) {
        this.lopHocPhanRepo = lopHocPhanRepo;
        this.nhanvienRepo = nhanvienRepo;
    }

    private Nhanvien getNhanvienByEmail(String email) {
        return nhanvienRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên: " + email));
    }

    public Map<Integer, List<LopHocPhan>> getLichTheoThu(String email, Integer hocKy, String namHoc) {
        int id = getNhanvienByEmail(email).getId();

        List<LopHocPhan> danhSach = lopHocPhanRepo
                .findByGiangVien_IdAndHocKyAndNamHoc(id, hocKy, namHoc);

        // Nhóm theo thứ trong tuần (2 → 7)
        Map<Integer, List<LopHocPhan>> lichTheoThu = new LinkedHashMap<>();
        for (int thu = 2; thu <= 7; thu++) {
            lichTheoThu.put(thu, new ArrayList<>());
        }
        for (LopHocPhan lhp : danhSach) {
            if (lhp.getThuTrongTuan() != null) {
                lichTheoThu.get(lhp.getThuTrongTuan()).add(lhp);
            }
        }
        return lichTheoThu;
    }
}
