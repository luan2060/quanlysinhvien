package com.example.quanlysinhvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlysinhvien.Model.ChuongTrinhDaoTao;

@Repository
public interface ChuongTrinhDaoTaoRepository extends JpaRepository<ChuongTrinhDaoTao, Integer> {
    long countByTrangThai(String trangThai);
}
