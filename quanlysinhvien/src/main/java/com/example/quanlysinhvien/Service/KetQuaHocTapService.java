package com.example.quanlysinhvien.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlysinhvien.Model.KetQuaHocTap;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.repository.KetQuaHocTapRepository;
import com.example.quanlysinhvien.repository.SinhVienRepository;

import jakarta.transaction.Transactional;

// KetQuaHocTapService.java
@Service
@Transactional
public class KetQuaHocTapService {

    private final KetQuaHocTapRepository ketQuaRepo;
    private final SinhVienRepository sinhVienRepo;

    public KetQuaHocTapService(KetQuaHocTapRepository ketQuaRepo, SinhVienRepository sinhVienRepo) {
        this.ketQuaRepo = ketQuaRepo;
        this.sinhVienRepo = sinhVienRepo;
    }

    private SinhVien getSinhVienByEmail(String email) {
        return sinhVienRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
    }

    public List<KetQuaHocTap> getAllKetQua(String email) {
        int id = getSinhVienByEmail(email).getId();
        return ketQuaRepo.findBySinhVien_Id(id);
    }

    public List<KetQuaHocTap> getKetQuaByHocKy(String email, Integer hocKy, String namHoc) {
        int id = getSinhVienByEmail(email).getId();
        return ketQuaRepo.findBySinhVien_IdAndLopHocPhan_HocKyAndLopHocPhan_NamHoc(id, hocKy, namHoc);
    }

    // Tính GPA hệ 4
    public double tinhGPA(List<KetQuaHocTap> danhSach) {
        double tongTinChi = 0, tongDiem = 0;
        for (KetQuaHocTap kq : danhSach) {
            if (kq.getDiemTongKet() == null)
                continue;
            int tc = kq.getLopHocPhan().getHocPhan().getSoTinChi(); // điều chỉnh nếu field khác
            tongTinChi += tc;
            tongDiem += diemChu4(kq.getDiemTongKet()) * tc;
        }
        return tongTinChi == 0 ? 0 : Math.round((tongDiem / tongTinChi) * 100.0) / 100.0;
    }

    // Tính điểm TB hệ 10
    public double tinhTBHe10(List<KetQuaHocTap> danhSach) {
        return danhSach.stream()
                .filter(kq -> kq.getDiemTongKet() != null)
                .mapToDouble(kq -> kq.getDiemTongKet())
                .average().orElse(0);
    }

    // Tổng tín chỉ tích lũy (chỉ môn qua)
    public int tinhTinChiTichLuy(List<KetQuaHocTap> danhSach) {
        return danhSach.stream()
                .filter(kq -> Boolean.TRUE.equals(kq.getTrangThaiQuaMon()))
                .mapToInt(kq -> kq.getLopHocPhan().getHocPhan().getSoTinChi())
                .sum();
    }

    // Xếp loại
    public String xepLoai(double gpa) {
        if (gpa >= 3.6)
            return "Xuất sắc";
        if (gpa >= 3.2)
            return "Giỏi";
        if (gpa >= 2.5)
            return "Khá";
        if (gpa >= 2.0)
            return "Trung bình";
        return "Yếu";
    }

    private double diemChu4(float diem) {
        if (diem >= 9.0)
            return 4.0;
        if (diem >= 8.5)
            return 3.7;
        if (diem >= 8.0)
            return 3.5;
        if (diem >= 7.0)
            return 3.0;
        if (diem >= 6.5)
            return 2.5;
        if (diem >= 6.0)
            return 2.0;
        if (diem >= 5.5)
            return 1.5;
        if (diem >= 5.0)
            return 1.0;
        return 0.0;
    }
}