package com.example.quanlysinhvien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quanlysinhvien.Model.LopHocPhan;

@Repository
public interface LopHocPhanRepository extends JpaRepository<LopHocPhan, Integer> {
        // ✅ Đảm bảo có method này — tên field phải khớp với entity
        List<LopHocPhan> findByHocKyAndNamHoc(Integer hocKy, String namHoc);

        // ✅ Nếu muốn fetch luôn hocPhan và giangVien tránh LazyLoading
        @Query("SELECT l FROM LopHocPhan l " +
                        "LEFT JOIN FETCH l.hocPhan " +
                        "LEFT JOIN FETCH l.giangVien " +
                        "LEFT JOIN FETCH l.danhSachKetQua " +
                        "WHERE l.hocKy = :hocKy AND l.namHoc = :namHoc")
        List<LopHocPhan> findByHocKyAndNamHocWithDetails(
                        @Param("hocKy") Integer hocKy,
                        @Param("namHoc") String namHoc);

        boolean existsByMaLopHP(String maLopHP);

        @Query("SELECT COUNT(k) FROM KetQuaHocTap k WHERE k.lopHocPhan.id = :lopId")
        int demSoSinhVienDangKy(@Param("lopId") int lopId);

        List<LopHocPhan> findByHocPhanId(Integer hocPhanId);

        List<LopHocPhan> findByGiangVien_IdAndHocKyAndNamHoc(
                        int giangVienId, Integer hocKy, String namHoc);

        // Lấy tất cả lớp theo giảng viên
        List<LopHocPhan> findByGiangVien_Id(int giangVienId);

        // dành cho kết quả học tập
        @Query("SELECT l FROM LopHocPhan l JOIN FETCH l.hocPhan " +
                        "WHERE l.giangVien.id = :idGV ORDER BY l.maLopHP")
        List<LopHocPhan> findByGiangVienId(@Param("idGV") int idGV);

}
