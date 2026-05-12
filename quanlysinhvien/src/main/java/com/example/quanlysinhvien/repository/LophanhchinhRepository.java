package com.example.quanlysinhvien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quanlysinhvien.Model.LopHanhChinh;

@Repository
public interface LophanhchinhRepository extends JpaRepository<LopHanhChinh, Integer> {
    @Query("SELECT COUNT(l) FROM LopHanhChinh l WHERE l.nganh.khoa.id = :khoaId")
    long countLopByKhoaId(@Param("khoaId") int khoaId);

    long count();

    @Query("SELECT l FROM LopHanhChinh l WHERE " +
            "LOWER(l.maLopHc) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.tenLopHc) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<LopHanhChinh> searchByKeyword(@Param("keyword") String keyword);

    long countByTrangThai(String trangThai);
}
