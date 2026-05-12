package com.example.quanlysinhvien.Service;

import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.Model.UpdateProfileDTO;

public interface SinhVienIFService {

    // Lấy thông tin sinh viên đang đăng nhập
    SinhVien getCurrentSinhVien();

    // Cập nhật thông tin cá nhân (chỉ sdt + diaChi)
    void updateProfile(UpdateProfileDTO dto);

}
