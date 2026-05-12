package com.example.quanlysinhvien.Service;

import com.example.quanlysinhvien.Model.LopHocPhan;

import java.util.List;
import java.util.Set;

public interface DangKyHocPhanService {

    // Lấy tất cả lớp học phần theo học kỳ + năm học
    List<LopHocPhan> getDanhSachLop(Integer hocKy, String namHoc);

    // Lấy danh sách id lớp mà sinh viên hiện tại đã đăng ký
    Set<Integer> getIdLopDaDangKy(Integer hocKy, String namHoc);

    // Đếm sĩ số hiện tại của 1 lớp
    int getSiSoHienTai(int lopId);

    // Đăng ký học phần
    void dangKy(int lopHocPhanId);

    // Hủy đăng ký học phần
    void huyDangKy(int lopHocPhanId);

    // Tính tổng tín chỉ đã đăng ký trong kỳ
    int getTongTinChi(Integer hocKy, String namHoc);
}
