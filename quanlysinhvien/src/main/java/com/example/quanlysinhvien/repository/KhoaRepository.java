package com.example.quanlysinhvien.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quanlysinhvien.Model.Khoa;

@Repository
public interface KhoaRepository extends JpaRepository<Khoa, Integer> {
    long count();

    boolean existsByMaKhoa(String maKhoa);

    @Query("""
                SELECT k
                FROM Khoa k
                WHERE LOWER(k.tenKhoa) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(k.maKhoa) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<Khoa> searchByKeyword(@Param("keyword") String keyword);
}