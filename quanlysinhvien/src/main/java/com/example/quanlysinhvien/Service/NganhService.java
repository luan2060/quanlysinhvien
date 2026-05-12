package com.example.quanlysinhvien.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quanlysinhvien.Model.Khoa;
import com.example.quanlysinhvien.Model.Nganh;
import com.example.quanlysinhvien.repository.KhoaRepository;
import com.example.quanlysinhvien.repository.NganhRepository;

import jakarta.transaction.Transactional;

// NganhService.java
@Service
@Transactional
public class NganhService {

    @Autowired
    private NganhRepository nganhRepository;

    @Autowired
    private KhoaRepository khoaRepository;

    public List<Nganh> findAll() {
        return nganhRepository.findAll();
    }

    public List<Nganh> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return nganhRepository.findAll();
        }
        return nganhRepository.searchByKeyword(keyword.trim());
    }

    public Optional<Nganh> findById(Integer id) {
        return nganhRepository.findById(id);
    }

    public void save(Nganh nganh, Integer khoaId) {
        if (khoaId != null) {
            Khoa khoa = khoaRepository.findById(khoaId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa với id: " + khoaId));
            nganh.setKhoa(khoa);
        }
        nganhRepository.save(nganh);
    }

    public void update(Nganh nganh, Integer khoaId) {
        Nganh existing = nganhRepository.findById(nganh.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành với id: " + nganh.getId()));

        existing.setTenNganh(nganh.getTenNganh());
        existing.setTrangThai(nganh.getTrangThai());

        if (khoaId != null) {
            Khoa khoa = khoaRepository.findById(khoaId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa với id: " + khoaId));
            existing.setKhoa(khoa);
        }

        nganhRepository.save(existing);
    }

    public void deleteById(Integer id) {
        nganhRepository.deleteById(id);
    }

    public long countHoatDong() {
        return nganhRepository.countByTrangThai("Đang hoạt động");
    }

    public long countTamDung() {
        return nganhRepository.countByTrangThai("Tạm dừng");
    }
}
