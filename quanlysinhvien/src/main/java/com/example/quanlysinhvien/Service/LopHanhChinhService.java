package com.example.quanlysinhvien.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quanlysinhvien.Model.Khoa;
import com.example.quanlysinhvien.Model.LopHanhChinh;
import com.example.quanlysinhvien.Model.Nganh;
import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.repository.LophanhchinhRepository;
import com.example.quanlysinhvien.repository.NganhRepository;
import com.example.quanlysinhvien.repository.NhanvienRepository;

@Service
public class LopHanhChinhService {
    private final LophanhchinhRepository lophanhchinhRepository;

    public LopHanhChinhService(LophanhchinhRepository lophanhchinhRepository) {
        this.lophanhchinhRepository = lophanhchinhRepository;
    }

    public List<LopHanhChinh> findlophochanhchinh() {
        List<LopHanhChinh> lopHanhChinhs = this.lophanhchinhRepository.findAll();
        return lopHanhChinhs;
    }

    public LopHanhChinh findById(int id) {
        return this.lophanhchinhRepository.findById(id).orElse(null);

    }

    public long countLopByKhoa(int khoaId) {
        return this.lophanhchinhRepository.countLopByKhoaId(khoaId);
    }

    public long counttotallop() {
        return this.lophanhchinhRepository.count();
    }

    @Autowired
    private LophanhchinhRepository lopRepository;

    @Autowired
    private NganhRepository nganhRepository;

    @Autowired
    private NhanvienRepository nhanvienRepository; // hoặc tên repo Nhanvien của bạn

    public List<LopHanhChinh> findAll() {
        return lopRepository.findAll();
    }

    public List<LopHanhChinh> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return lopRepository.findAll();
        }
        return lopRepository.searchByKeyword(keyword.trim());
    }

    public Optional<LopHanhChinh> findById(Integer id) {
        return lopRepository.findById(id);
    }

    public void save(LopHanhChinh lop, Integer nganhId, Integer covanId) {
        if (nganhId != null) {
            Nganh nganh = nganhRepository.findById(nganhId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành!"));
            lop.setNganh(nganh);
        }
        if (covanId != null) {
            Nhanvien coVan = nhanvienRepository.findById(covanId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy cố vấn!"));
            lop.setCoVan(coVan);
        }
        lopRepository.save(lop);
    }

    public void update(LopHanhChinh lop, Integer nganhId, Integer covanId) {
        LopHanhChinh existing = lopRepository.findById(lop.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp!"));

        existing.setTenLopHc(lop.getTenLopHc());
        existing.setKhoaHoc(lop.getKhoaHoc());
        existing.setTrangThai(lop.getTrangThai());

        if (nganhId != null) {
            Nganh nganh = nganhRepository.findById(nganhId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành!"));
            existing.setNganh(nganh);
        }
        if (covanId != null) {
            Nhanvien coVan = nhanvienRepository.findById(covanId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy cố vấn!"));
            existing.setCoVan(coVan);
        }

        lopRepository.save(existing);
    }

    public void deleteById(Integer id) {
        lopRepository.deleteById(id);
    }

    public long countDangHoc() {
        return lopRepository.countByTrangThai("Đang học");
    }

    public long countDaTotNghiep() {
        return lopRepository.countByTrangThai("Đã tốt nghiệp");
    }

}
