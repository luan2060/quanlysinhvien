package com.example.quanlysinhvien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlysinhvien.Model.KetQuaHocTap;

@Repository
public interface KetQuaRepository extends JpaRepository<KetQuaHocTap, Integer> {

    // Lấy danh sách điểm theo lớp học phần → dùng trong trang chitiet.html
    List<KetQuaHocTap> findByLopHocPhanId(Integer lopHocPhanId);

    // Kiểm tra sinh viên đã có bản ghi điểm trong lớp chưa (dùng khi thêm mới)
    boolean existsBySinhVienIdAndLopHocPhanId(Integer sinhVienId, Integer lopHocPhanId);
}
