package com.example.quanlysinhvien.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlysinhvien.Model.ChuongTrinhDaoTao;
import com.example.quanlysinhvien.Model.Nganh;
import com.example.quanlysinhvien.repository.ChuongTrinhDaoTaoRepository;
import com.example.quanlysinhvien.repository.NganhRepository;

@Service
public class ChuongTrinhDaoTaoService {

    private final ChuongTrinhDaoTaoRepository ctdtRepo;
    private final NganhRepository nganhRepo;

    public ChuongTrinhDaoTaoService(ChuongTrinhDaoTaoRepository ctdtRepo,
            NganhRepository nganhRepo) {
        this.ctdtRepo = ctdtRepo;
        this.nganhRepo = nganhRepo;
    }

    public List<ChuongTrinhDaoTao> getAll() {
        return ctdtRepo.findAll();
    }

    public ChuongTrinhDaoTao getById(int id) {
        return ctdtRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy CTĐT"));
    }

    public void save(ChuongTrinhDaoTao ctdt) {
        ctdtRepo.save(ctdt);
    }

    public void delete(int id) {
        ctdtRepo.deleteById(id);
    }

    public long countDangApDung() {
        return ctdtRepo.countByTrangThai("Hoạt động");
    }

    public long countNgungApDung() {
        return ctdtRepo.countByTrangThai("Tạm dừng");
    }

    public List<Nganh> getAllNganh() {
        return nganhRepo.findAll();
    }
}
