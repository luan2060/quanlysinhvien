package com.example.quanlysinhvien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.quanlysinhvien.Model.Nganh;

public interface NganhRepository extends JpaRepository<Nganh, Integer> {
    // Tìm kiếm theo mã hoặc tên ngành (không phân biệt hoa thường)
    @Query("SELECT n FROM Nganh n WHERE " +
            "LOWER(n.maNganh) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.tenNganh) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Nganh> searchByKeyword(@Param("keyword") String keyword);

    // Đếm theo trạng thái cho thống kê
    long countByTrangThai(String trangThai);
}
