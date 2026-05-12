package com.example.quanlysinhvien.Service;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.quanlysinhvien.Model.Khoa;
import com.example.quanlysinhvien.Model.LopHanhChinh;
import com.example.quanlysinhvien.Model.Nhanvien;
import com.example.quanlysinhvien.repository.KhoaRepository;

@Service
public class KhoaService {
    // private final KhoaRepository khoaRepository;

    // public KhoaService(KhoaRepository khoaRepository) {
    // this.khoaRepository = khoaRepository;
    // }

    // public List<Khoa> fetchKhoas() {
    // List<Khoa> khoaList = this.khoaRepository.findAll();
    // return khoaList;
    // }

    // public void createKhoa(Khoa khoa) {
    // this.khoaRepository.save(khoa);
    // }

    // public Khoa findKhoaById(int id) {
    // Optional<Khoa> updateKhoa = this.khoaRepository.findById(id);
    // return updateKhoa.get();
    // }

    // public void updateKhoa(Khoa inputKhoa) {
    // Khoa currentKhoaInDB = this.findKhoaById(inputKhoa.getId());
    // if (currentKhoaInDB != null) {
    // currentKhoaInDB.setMaKhoa(inputKhoa.getMaKhoa());
    // currentKhoaInDB.setTenKhoa(inputKhoa.getTenKhoa());

    // this.khoaRepository.save(currentKhoaInDB);
    // }

    // }

    // public void deleteKhoaById(int id) {
    // this.khoaRepository.deleteById(id);

    // }
    private final KhoaRepository khoaRepository;

    public KhoaService(KhoaRepository khoaRepository) {
        this.khoaRepository = khoaRepository;
    }

    public List<Khoa> countkhoa() {
        List<Khoa> listkhoa = this.khoaRepository.findAll();
        return listkhoa;
    }

    public long counttotalkhoa() {
        return this.khoaRepository.count();
    }

    public Khoa findById(int id) {
        return this.khoaRepository.findById(id).orElse(null);
    }

    public void createkhoa(Khoa khoa) {
        this.khoaRepository.save(khoa);
    }

    public boolean checkMaKhoaExists(String maKhoa) {
        return this.khoaRepository.existsByMaKhoa(maKhoa);
    }

    public Khoa findKhoaById(int id) {
        Optional<Khoa> updatekhoa = this.khoaRepository.findById(id);
        return updatekhoa.get();
    }

    public void deletekhoaById(int id) {
        this.khoaRepository.deleteById(id);
    }

    public void updatekhoa(Khoa inputKhoa) {
        // 1. Tìm Khoa hiện tại trong Database bằng ID
        Optional<Khoa> khoaOptional = this.khoaRepository.findById(inputKhoa.getId());

        if (khoaOptional.isPresent()) {
            Khoa currentKhoa = khoaOptional.get();

            // 2. Cập nhật các thông tin cơ bản
            // Lưu ý: maKhoa là duy nhất, thường chúng ta sẽ không cho phép sửa ở giao diện
            // Nhưng nếu form của bạn cho phép sửa, dòng này sẽ cập nhật nó:
            currentKhoa.setMaKhoa(inputKhoa.getMaKhoa());

            currentKhoa.setTenKhoa(inputKhoa.getTenKhoa());
            currentKhoa.setNamThanhLap(inputKhoa.getNamThanhLap());
            currentKhoa.setThongTinLienHe(inputKhoa.getThongTinLienHe());
            currentKhoa.setTruongKhoa(inputKhoa.getTruongKhoa());
            currentKhoa.setTrangThai(inputKhoa.getTrangThai());

            // 3. Lưu lại vào Database
            this.khoaRepository.save(currentKhoa);
        }
    }

    public List<Khoa> searchKhoa(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {

            return khoaRepository.searchByKeyword(keyword.trim());
        }
        return khoaRepository.findAll();
    }

}
