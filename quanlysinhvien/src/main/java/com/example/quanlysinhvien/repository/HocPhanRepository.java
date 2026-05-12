package com.example.quanlysinhvien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.quanlysinhvien.Model.HocPhan;

public interface HocPhanRepository extends JpaRepository<HocPhan, Integer> {

    // Lấy tất cả kèm ngành (tránh LazyInitializationException)
    @Query("SELECT hp FROM HocPhan hp LEFT JOIN FETCH hp.nganh")
    List<HocPhan> findAllWithNganh();

    // Tìm kiếm theo mã hoặc tên
    @Query("SELECT hp FROM HocPhan hp LEFT JOIN FETCH hp.nganh " +
            "WHERE LOWER(hp.maHocPhan) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(hp.tenHocPhan) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<HocPhan> searchByKeyword(@Param("keyword") String keyword);
}
