package com.example.quanlysinhvien.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.Model.SinhVien;

@Repository
public interface NhanvienRepository extends JpaRepository<Nhanvien, Integer> {
    // long countByTrangThai(String trangThai);

    long countByTrangThai(String trangThai);

    long count();

    boolean existsByMaNV(String maNV);

    boolean existsByEmail(String email);

    @Query("""
                SELECT nv
                FROM Nhanvien nv
                WHERE LOWER(nv.tenNV) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(nv.maNV) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<Nhanvien> searchNhanVien(@Param("keyword") String keyword);

    Optional<Nhanvien> findByEmail(String email);

}
