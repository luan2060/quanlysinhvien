package com.example.quanlysinhvien.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quanlysinhvien.Model.KhoaPhanTramDTO;
import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.Model.SinhVien;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, Integer> {
    long countByTrangThai(String trangThai);

    // List<Student> findByTenSVContainingIgnoreCase(String hoTen);
    long count();

    boolean existsByMaSV(String maSV);

    @Query("SELECT s FROM SinhVien s WHERE LOWER(s.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.maSV) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<SinhVien> searchByNameOrId(@Param("keyword") String keyword);

    @Query("SELECT COUNT(s) FROM SinhVien s WHERE s.lopHanhChinh.nganh.khoa.id = :khoaId")
    long countByKhoaId(@Param("khoaId") int khoaId);

    @Query("""
                SELECT new com.example.quanlysinhvien.Model.KhoaPhanTramDTO(
                    k.tenKhoa,
                    COUNT(sv)
                )
                FROM SinhVien sv
                JOIN sv.lopHanhChinh lhc
                JOIN lhc.nganh n
                JOIN n.khoa k
                GROUP BY k.id, k.tenKhoa
                ORDER BY COUNT(sv) DESC
            """)
    List<KhoaPhanTramDTO> demSinhVienTheoKhoa();

    Optional<SinhVien> findByEmail(String email);

    Optional<SinhVien> findByMaSV(String maSV);

}
