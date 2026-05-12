package com.example.quanlysinhvien.Service;

import java.util.List;

import com.example.quanlysinhvien.Model.KetQuaHocTap;
import com.example.quanlysinhvien.Model.LopHocPhan;
import com.example.quanlysinhvien.Model.Nhanvien;

public interface GiangVienService {
    List<LopHocPhan> getDanhSachLopPhuTrach(int idGV);

    LopHocPhan getLopById(int idLop);

    List<KetQuaHocTap> getDanhSachKetQua(int idLop);

    void luuBangDiem(int idLopHP,
            List<Integer> danhSachIdSV,
            List<Float> danhSachCC,
            List<Float> danhSachGK,
            List<Float> danhSachCK);

    Nhanvien findByEmail(String email);
}
