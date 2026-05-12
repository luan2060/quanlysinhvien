package com.example.quanlysinhvien.Service;

import com.example.quanlysinhvien.Model.KetQuaHocTap;
import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.repository.KetQuaHocTapRepository;
import com.example.quanlysinhvien.repository.LopHocPhanRepository;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ThoiKhoaBieuService {

    private final LopHocPhanRepository lopHocPhanRepository;
    private final KetQuaHocTapRepository ketQuaHocTapRepository;

    // ← Sửa constructor, thêm tham số còn thiếu
    public ThoiKhoaBieuService(LopHocPhanRepository lopHocPhanRepository,
            KetQuaHocTapRepository ketQuaHocTapRepository) {
        this.lopHocPhanRepository = lopHocPhanRepository;
        this.ketQuaHocTapRepository = ketQuaHocTapRepository;
    }

    public Map<Integer, List<LopHocPhan>> getLichHocBySinhVien(Long svId, Integer hocKy, String namHoc) {
        Map<Integer, List<LopHocPhan>> lichHoc = new TreeMap<>();
        for (int i = 2; i <= 7; i++) {
            lichHoc.put(i, new ArrayList<>());
        }

        List<KetQuaHocTap> ketQua = ketQuaHocTapRepository
                .findBySinhVien_IdAndLopHocPhan_HocKyAndLopHocPhan_NamHoc(svId, hocKy, namHoc);

        for (KetQuaHocTap kq : ketQua) {
            LopHocPhan lop = kq.getLopHocPhan();
            Integer thu = lop.getThuTrongTuan();
            if (thu != null && thu >= 2 && thu <= 7) {
                lichHoc.get(thu).add(lop);
            }
        }

        return lichHoc;
    }
}