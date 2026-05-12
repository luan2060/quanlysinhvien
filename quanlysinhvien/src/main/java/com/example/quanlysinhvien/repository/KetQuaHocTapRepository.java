package com.example.quanlysinhvien.repository;

import com.example.quanlysinhvien.Model.KetQuaHocTap;
import com.example.quanlysinhvien.Model.Nhanvien;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KetQuaHocTapRepository extends JpaRepository<KetQuaHocTap, Integer> {

        // Kiểm tra sinh viên đã đăng ký lớp này chưa
        Optional<KetQuaHocTap> findBySinhVien_IdAndLopHocPhan_Id(int sinhVienId, int lopHocPhanId);

        // Lấy danh sách lớp sinh viên đã đăng ký
        List<KetQuaHocTap> findBySinhVien_Id(int sinhVienId);

        // Lấy danh sách lớp sinh viên đã đăng ký theo học kỳ và năm học
        List<KetQuaHocTap> findBySinhVien_IdAndLopHocPhan_HocKyAndLopHocPhan_NamHoc(
                        int sinhVienId, Integer hocKy, String namHoc);

        @Query("SELECT b FROM KetQuaHocTap b JOIN FETCH b.sinhVien sv " +
                        "WHERE b.lopHocPhan.id = :idLop ORDER BY sv.hoTen")
        List<KetQuaHocTap> findByLopHocPhanId(@Param("idLop") int idLop);

        // Kiểm tra SV đã có row trong lớp này chưa
        Optional<KetQuaHocTap> findByLopHocPhanIdAndSinhVienId(int idLop, int idSV);
        // Kiểm tra trong KetQuaHocTapRepository, tên method có thể là:

        // Hoặc nếu chưa có, thêm vào KetQuaHocTapRepository:
        List<KetQuaHocTap> findBySinhVien_IdAndLopHocPhan_HocKyAndLopHocPhan_NamHoc(
                        Long sinhVienId, Integer hocKy, String namHoc);

}
